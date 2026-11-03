CREATE TABLE trainees (
    id                  UUID         NOT NULL DEFAULT gen_random_uuid(),
    first_name          VARCHAR(80)  NOT NULL,
    last_name           VARCHAR(80)  NOT NULL,
    avatar_key          VARCHAR(255) NULL,
    avatar_file_id      VARCHAR(255) NULL,
    user_id             UUID         NOT NULL,
    personal_trainer_id UUID         NULL,
    CONSTRAINT pk_trainees PRIMARY KEY (id),
    CONSTRAINT uq_trainees_user UNIQUE (user_id),
    CONSTRAINT fk_trainees_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_trainees_personal_trainer FOREIGN KEY (personal_trainer_id) REFERENCES personals_trainers (id) ON DELETE SET NULL
);
