'use client';

import { useMemo, useState } from 'react';

export type CourseQuizQuestion = {
  prompt: string;
  choices: string[];
  answer: number;
  explanation: string;
};

export function CourseQuiz({ questions }: { questions: CourseQuizQuestion[] }) {
  const [answers, setAnswers] = useState<Record<number, number>>({});
  const [submitted, setSubmitted] = useState(false);

  const score = useMemo(
    () => questions.reduce((total, question, index) => total + (answers[index] === question.answer ? 1 : 0), 0),
    [answers, questions],
  );

  const complete = Object.keys(answers).length === questions.length;

  function reset() {
    setAnswers({});
    setSubmitted(false);
  }

  return (
    <section className="my-8 space-y-6" aria-label="Module quiz">
      {questions.map((question, questionIndex) => (
        <fieldset
          key={question.prompt}
          className="rounded-xl border border-fd-border bg-fd-card p-5 shadow-sm"
        >
          <legend className="px-2 text-base font-semibold text-fd-foreground">
            {questionIndex + 1}. {question.prompt}
          </legend>
          <div className="mt-3 grid gap-2">
            {question.choices.map((choice, choiceIndex) => {
              const selected = answers[questionIndex] === choiceIndex;
              const correct = submitted && choiceIndex === question.answer;
              const incorrect = submitted && selected && choiceIndex !== question.answer;

              return (
                <label
                  key={choice}
                  className={`flex cursor-pointer gap-3 rounded-lg border px-4 py-3 text-sm transition-colors ${
                    correct
                      ? 'border-emerald-500 bg-emerald-500/10'
                      : incorrect
                        ? 'border-red-500 bg-red-500/10'
                        : selected
                          ? 'border-fd-primary bg-fd-primary/10'
                          : 'border-fd-border hover:bg-fd-muted'
                  }`}
                >
                  <input
                    type="radio"
                    name={`question-${questionIndex}`}
                    value={choiceIndex}
                    checked={selected}
                    disabled={submitted}
                    onChange={() =>
                      setAnswers((current) => ({ ...current, [questionIndex]: choiceIndex }))
                    }
                    className="mt-0.5 accent-fd-primary"
                  />
                  <span>{choice}</span>
                </label>
              );
            })}
          </div>
          {submitted && (
            <p className="mt-4 rounded-lg bg-fd-muted px-4 py-3 text-sm">
              <strong>{answers[questionIndex] === question.answer ? 'Correct.' : 'Review this one.'}</strong>{' '}
              {question.explanation}
            </p>
          )}
        </fieldset>
      ))}

      {!submitted ? (
        <button
          type="button"
          disabled={!complete}
          onClick={() => setSubmitted(true)}
          className="rounded-lg bg-fd-primary px-5 py-3 font-semibold text-white disabled:cursor-not-allowed disabled:opacity-40"
        >
          Check answers
        </button>
      ) : (
        <div className="flex flex-wrap items-center gap-4 rounded-xl border border-fd-primary/40 bg-fd-primary/10 p-5">
          <p className="m-0 text-lg font-semibold">
            Score: {score} / {questions.length}
          </p>
          <button
            type="button"
            onClick={reset}
            className="rounded-lg border border-fd-border bg-fd-card px-4 py-2 font-medium hover:bg-fd-muted"
          >
            Try again
          </button>
        </div>
      )}
    </section>
  );
}
