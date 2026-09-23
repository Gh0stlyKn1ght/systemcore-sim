import java.util.ArrayList;
import java.util.List;

public final class RegressionSuite {
  private static final double DEADBAND = 0.08;
  private static final long MAX_AGE_MS = 100;

  record Input(boolean enabled, double forward, double turn, long ageMs) {}
  record Output(double left, double right, String reason) {}

  static Output decide(Input input) {
    if (!input.enabled()) return neutral("disabled");
    if (!Double.isFinite(input.forward()) || !Double.isFinite(input.turn()) || input.ageMs() < 0) {
      return neutral("invalid");
    }
    if (input.ageMs() > MAX_AGE_MS) return neutral("stale");

    double forward = Math.abs(input.forward()) <= DEADBAND ? 0.0 : input.forward();
    double turn = Math.abs(input.turn()) <= DEADBAND ? 0.0 : input.turn();
    if (forward == 0.0 && turn == 0.0) return neutral("deadband");
    return new Output(clamp(forward + turn), clamp(forward - turn), "ready");
  }

  static Output mutantRejectsBoundary(Input input) {
    if (input.ageMs() >= MAX_AGE_MS) return neutral("stale");
    return decide(input);
  }

  public static void main(String[] args) {
    List<String> failures = new ArrayList<>();

    expectReason(failures, "99 ms accepted", decide(new Input(true, 1, 0, 99)), "ready");
    expectReason(failures, "100 ms accepted", decide(new Input(true, 1, 0, 100)), "ready");
    expectReason(failures, "101 ms stale", decide(new Input(true, 1, 0, 101)), "stale");
    expectReason(failures, "negative age invalid", decide(new Input(true, 1, 0, -1)), "invalid");
    expectReason(failures, "disabled is neutral", decide(new Input(false, 1, 0, 0)), "disabled");
    expectReason(failures, "NaN invalid", decide(new Input(true, Double.NaN, 0, 0)), "invalid");
    expectReason(failures, "deadband edge neutral", decide(new Input(true, 0.08, 0, 0)), "deadband");
    expectReason(failures, "above deadband active", decide(new Input(true, 0.081, 0, 0)), "ready");

    int swept = 0;
    for (int forwardStep = -20; forwardStep <= 20; forwardStep++) {
      for (int turnStep = -20; turnStep <= 20; turnStep++) {
        Output output = decide(new Input(true, forwardStep / 10.0, turnStep / 10.0, 0));
        check(failures, "left output bounded", Math.abs(output.left()) <= 1.0);
        check(failures, "right output bounded", Math.abs(output.right()) <= 1.0);
        swept++;
      }
    }

    for (int step = -20; step <= 20; step++) {
      Output output = decide(new Input(false, step / 10.0, -step / 10.0, 0));
      check(failures, "disabled sweep left neutral", output.left() == 0.0);
      check(failures, "disabled sweep right neutral", output.right() == 0.0);
    }

    Output correctBoundary = decide(new Input(true, 1, 0, 100));
    Output mutatedBoundary = mutantRejectsBoundary(new Input(true, 1, 0, 100));
    check(failures, "mutation changes the protected boundary", !correctBoundary.equals(mutatedBoundary));
    check(failures, "boundary test detects mutation", mutatedBoundary.reason().equals("stale"));

    if (!failures.isEmpty()) {
      failures.forEach(failure -> System.err.println("FAIL " + failure));
      throw new AssertionError(failures.size() + " automated checks failed");
    }

    System.out.println("PASS: 10 named regressions, " + swept + " input pairs, and mutation detection");
  }

  private static Output neutral(String reason) {
    return new Output(0.0, 0.0, reason);
  }

  private static double clamp(double value) {
    return Math.max(-1.0, Math.min(1.0, value));
  }

  private static void expectReason(
      List<String> failures, String name, Output actual, String expectedReason) {
    check(failures, name + " expected=" + expectedReason + " actual=" + actual.reason(), actual.reason().equals(expectedReason));
  }

  private static void check(List<String> failures, String name, boolean condition) {
    if (!condition) failures.add(name);
  }
}
