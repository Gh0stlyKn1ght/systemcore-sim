import java.util.ArrayList;
import java.util.List;

public final class AutonomousRoutineCheck {
  private static final double DRIVE_TARGET_METERS = 1.0;
  private static final double TURN_TARGET_RADIANS = Math.PI / 2.0;
  private static final double HEADING_TOLERANCE_RADIANS = 0.05;
  private static final int STEP_TIMEOUT_TICKS = 100;
  private static final long MAX_OBSERVATION_AGE_MS = 100;

  enum State { IDLE, DRIVE, TURN, COMPLETE, CANCELED, FAILED }

  record Observation(double distanceMeters, double headingRadians, long ageMs, boolean valid) {}
  record Request(double left, double right) {
    static Request neutral() { return new Request(0.0, 0.0); }
  }
  record Result(State state, Request request, String reason) {}

  static final class Routine {
    private State state = State.IDLE;
    private int stateTicks;

    Result start() {
      if (state != State.IDLE) return current("start ignored");
      state = State.DRIVE;
      stateTicks = 0;
      return current("started");
    }

    Result update(boolean canceled, Observation observation) {
      if (terminal()) return current("terminal");
      if (state == State.IDLE) return current("not started");
      if (canceled) return terminate(State.CANCELED, "canceled");
      if (!usable(observation)) return terminate(State.FAILED, "invalid observation");

      return switch (state) {
        case DRIVE -> updateDrive(observation);
        case TURN -> updateTurn(observation);
        default -> current("terminal");
      };
    }

    private Result updateDrive(Observation observation) {
      if (observation.distanceMeters() >= DRIVE_TARGET_METERS) {
        state = State.TURN;
        stateTicks = 0;
        return current("drive complete");
      }
      if (stateTicks++ >= STEP_TIMEOUT_TICKS) {
        return terminate(State.FAILED, "drive timeout");
      }
      return new Result(state, new Request(0.5, 0.5), "driving");
    }

    private Result updateTurn(Observation observation) {
      double error = TURN_TARGET_RADIANS - observation.headingRadians();
      if (Math.abs(error) <= HEADING_TOLERANCE_RADIANS) {
        return terminate(State.COMPLETE, "turn complete");
      }
      if (stateTicks++ >= STEP_TIMEOUT_TICKS) {
        return terminate(State.FAILED, "turn timeout");
      }
      double direction = Math.copySign(0.4, error);
      return new Result(state, new Request(-direction, direction), "turning");
    }

    private boolean usable(Observation observation) {
      return observation != null
          && observation.valid()
          && observation.ageMs() >= 0
          && observation.ageMs() <= MAX_OBSERVATION_AGE_MS
          && Double.isFinite(observation.distanceMeters())
          && Double.isFinite(observation.headingRadians());
    }

    private Result terminate(State terminalState, String reason) {
      state = terminalState;
      return new Result(state, Request.neutral(), reason);
    }

    private boolean terminal() {
      return state == State.COMPLETE || state == State.CANCELED || state == State.FAILED;
    }

    private Result current(String reason) {
      return new Result(state, Request.neutral(), reason);
    }
  }

  public static void main(String[] args) {
    List<String> failures = new ArrayList<>();

    Routine normal = new Routine();
    normal.start();
    Result driving = normal.update(false, new Observation(0.5, 0.0, 0, true));
    expect(failures, "drive active", driving, State.DRIVE, new Request(0.5, 0.5));
    Result transition = normal.update(false, new Observation(1.0, 0.0, 0, true));
    expect(failures, "drive transitions neutral", transition, State.TURN, Request.neutral());
    Result complete = normal.update(false, new Observation(1.0, TURN_TARGET_RADIANS, 0, true));
    expect(failures, "normal completion neutral", complete, State.COMPLETE, Request.neutral());
    expect(failures, "complete remains terminal", normal.update(false, new Observation(0, 0, 0, true)), State.COMPLETE, Request.neutral());

    Routine cancelDrive = new Routine();
    cancelDrive.start();
    expect(failures, "cancel during drive", cancelDrive.update(true, new Observation(0, 0, 0, true)), State.CANCELED, Request.neutral());

    Routine cancelTurn = new Routine();
    cancelTurn.start();
    cancelTurn.update(false, new Observation(1.0, 0, 0, true));
    expect(failures, "cancel during turn", cancelTurn.update(true, new Observation(1, 0, 0, true)), State.CANCELED, Request.neutral());

    Routine timeout = new Routine();
    timeout.start();
    Result timedOut = null;
    for (int tick = 0; tick <= STEP_TIMEOUT_TICKS + 1; tick++) {
      timedOut = timeout.update(false, new Observation(0, 0, 0, true));
    }
    expect(failures, "drive timeout neutral", timedOut, State.FAILED, Request.neutral());

    Routine stale = new Routine();
    stale.start();
    expect(failures, "stale observation fails", stale.update(false, new Observation(0, 0, 101, true)), State.FAILED, Request.neutral());

    Routine invalid = new Routine();
    invalid.start();
    expect(failures, "non-finite observation fails", invalid.update(false, new Observation(Double.NaN, 0, 0, true)), State.FAILED, Request.neutral());

    Routine unauthorizedRestart = new Routine();
    unauthorizedRestart.start();
    unauthorizedRestart.update(true, new Observation(0, 0, 0, true));
    expect(failures, "terminal routine does not restart", unauthorizedRestart.start(), State.CANCELED, Request.neutral());

    if (!failures.isEmpty()) {
      failures.forEach(failure -> System.err.println("FAIL " + failure));
      throw new AssertionError(failures.size() + " autonomous checks failed");
    }
    System.out.println("PASS: 10 autonomous completion, cancellation, timeout, and fault checks");
  }

  private static void expect(
      List<String> failures, String name, Result actual, State state, Request request) {
    if (actual.state() != state || !actual.request().equals(request)) {
      failures.add(name + " expected=" + state + "/" + request + " actual=" + actual);
    }
  }
}
