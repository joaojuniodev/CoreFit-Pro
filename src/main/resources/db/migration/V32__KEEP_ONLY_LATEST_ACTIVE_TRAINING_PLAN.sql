WITH ranked_plans AS (
    SELECT id,
           ROW_NUMBER() OVER (PARTITION BY trainee_id ORDER BY id DESC) AS position
    FROM training_plans
    WHERE active = TRUE
)
UPDATE training_plans
SET active = FALSE
WHERE id IN (
    SELECT id
    FROM ranked_plans
    WHERE position > 1
);
