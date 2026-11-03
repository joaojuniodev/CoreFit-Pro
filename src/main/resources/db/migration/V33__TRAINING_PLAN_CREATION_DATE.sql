ALTER TABLE training_plans ADD COLUMN IF NOT EXISTS created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP;
CREATE UNIQUE INDEX IF NOT EXISTS uq_active_training_plan_per_trainee
    ON training_plans (trainee_id) WHERE active = TRUE;
