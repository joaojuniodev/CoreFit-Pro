CREATE TABLE exercises (
    id                  UUID         NOT NULL DEFAULT gen_random_uuid(),
    name                VARCHAR(100) NOT NULL,
    system              BOOLEAN      NOT NULL DEFAULT TRUE,
    favorite            BOOLEAN      NOT NULL DEFAULT FALSE,
    photo_key           VARCHAR(255) NULL,
    video_key           VARCHAR(255) NULL,
    photo_file_id       VARCHAR(255) NULL,
    video_file_id       VARCHAR(255) NULL,
    personal_trainer_id UUID         NULL,
    CONSTRAINT pk_exercises PRIMARY KEY (id),
    CONSTRAINT fk_exercises_personal_trainer FOREIGN KEY (personal_trainer_id) REFERENCES personals_trainers (id) ON DELETE CASCADE
);