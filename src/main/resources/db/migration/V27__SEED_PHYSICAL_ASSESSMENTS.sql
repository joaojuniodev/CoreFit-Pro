-- 3 avaliações físicas por aluno (a cada ~30 dias, com fotos de evolução em todas),
-- mostrando evolução condizente com o objetivo de cada plano de treino

DO $$
DECLARE
    mateus_id UUID;
    beatriz_id UUID;
    thiago_id UUID;
    larissa_id UUID;
    bruno_id UUID;
    gabriel_id UUID;
    camila_id UUID;
    amanda_id UUID;
    diego_id UUID;
    juliana_id UUID;
    felipe_id UUID;
    carolina_id UUID;
    eduardo_id UUID;
    renata_id UUID;
    vinicius_id UUID;
    isabela_id UUID;
BEGIN
    SELECT t.id INTO mateus_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'mateus';
    SELECT t.id INTO beatriz_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'beatriz';
    SELECT t.id INTO thiago_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'thiago';
    SELECT t.id INTO larissa_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'larissa';
    SELECT t.id INTO bruno_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'bruno';
    SELECT t.id INTO gabriel_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'gabriel';
    SELECT t.id INTO camila_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'camila';
    SELECT t.id INTO amanda_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'amanda';
    SELECT t.id INTO diego_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'diego';
    SELECT t.id INTO juliana_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'juliana';
    SELECT t.id INTO felipe_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'felipe';
    SELECT t.id INTO carolina_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'carolina';
    SELECT t.id INTO eduardo_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'eduardo';
    SELECT t.id INTO renata_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'renata';
    SELECT t.id INTO vinicius_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'vinicius';
    SELECT t.id INTO isabela_id FROM trainees t JOIN users u ON u.id = t.user_id WHERE u.username = 'isabela';

    -- Mateus
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), mateus_id, CURRENT_DATE - 60, 24, 78.0, 1.78, 24.62, 18.0, 14.0, 32.8, 99.7, 79.0, 82.0, 100.2, 33.4, 32.9, 56.6, 56.1, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), mateus_id, CURRENT_DATE - 30, 24, 79.0, 1.78, 24.93, 17.2, 13.6, 33.3, 99.8, 78.6, 81.6, 100.3, 33.5, 33.0, 56.7, 56.2, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), mateus_id, CURRENT_DATE - 2, 24, 79.9, 1.78, 25.22, 16.6, 13.3, 33.7, 100.0, 78.3, 81.3, 100.4, 33.6, 33.1, 56.8, 56.3, 'MODERATE', 'Boa evolução de massa magra, manter dieta hiperproteica', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Beatriz
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), beatriz_id, CURRENT_DATE - 60, 29, 70.0, 1.65, 25.71, 32.0, 22.4, 29.4, 98.5, 86.0, 89.0, 99.6, 32.4, 31.9, 55.7, 55.2, 'LIGHT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), beatriz_id, CURRENT_DATE - 30, 29, 68.0, 1.65, 24.98, 30.0, 20.4, 28.6, 98.2, 85.0, 88.0, 99.4, 32.2, 31.7, 55.5, 55.0, 'LIGHT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), beatriz_id, CURRENT_DATE - 2, 29, 66.1, 1.65, 24.28, 28.1, 18.6, 27.9, 97.9, 84.0, 87.0, 99.3, 31.9, 31.4, 55.3, 54.8, 'LIGHT', 'Ótima redução de percentual de gordura, seguir com cardio 3x/semana', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Thiago
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), thiago_id, CURRENT_DATE - 60, 31, 84.0, 1.8, 25.93, 24.0, 20.2, 35.3, 100.6, 82.0, 85.0, 100.7, 34.1, 33.6, 57.2, 56.7, 'LIGHT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), thiago_id, CURRENT_DATE - 30, 31, 82.5, 1.8, 25.46, 22.5, 18.6, 34.7, 100.4, 81.2, 84.2, 100.6, 33.9, 33.4, 57.1, 56.6, 'LIGHT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), thiago_id, CURRENT_DATE - 2, 31, 81.1, 1.8, 25.03, 21.1, 17.1, 34.2, 100.2, 80.5, 83.5, 100.5, 33.7, 33.2, 56.9, 56.4, 'LIGHT', 'Boa evolução de resistência, aumentar volume de cardio gradualmente', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Larissa
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), larissa_id, CURRENT_DATE - 60, 27, 63.0, 1.6, 24.61, 26.0, 16.4, 26.5, 97.5, 83.0, 86.0, 99.0, 31.6, 31.1, 54.9, 54.4, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), larissa_id, CURRENT_DATE - 30, 27, 62.2, 1.6, 24.3, 24.8, 15.4, 26.2, 97.3, 82.4, 85.4, 99.0, 31.5, 31.0, 54.8, 54.3, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), larissa_id, CURRENT_DATE - 2, 27, 61.5, 1.6, 24.02, 23.7, 14.6, 26.0, 97.2, 81.8, 84.8, 98.9, 31.4, 30.9, 54.8, 54.3, 'MODERATE', 'Definição muscular evoluindo de forma consistente', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Bruno
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), bruno_id, CURRENT_DATE - 60, 33, 92.0, 1.85, 26.88, 20.0, 18.4, 38.6, 101.8, 80.0, 83.0, 101.4, 35.0, 34.5, 58.1, 57.6, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), bruno_id, CURRENT_DATE - 30, 33, 93.2, 1.85, 27.23, 19.2, 17.9, 39.2, 102.0, 79.6, 82.6, 101.5, 35.2, 34.7, 58.3, 57.8, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), bruno_id, CURRENT_DATE - 2, 33, 94.3, 1.85, 27.55, 18.5, 17.4, 39.8, 102.1, 79.2, 82.2, 101.5, 35.3, 34.8, 58.4, 57.9, 'ACTIVE', 'Ganho de força notável no supino e agachamento', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Gabriel
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), gabriel_id, CURRENT_DATE - 60, 22, 75.0, 1.8, 23.15, 15.0, 11.2, 31.5, 99.2, 77.5, 80.5, 100.0, 33.0, 32.5, 56.2, 55.7, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), gabriel_id, CURRENT_DATE - 30, 22, 75.8, 1.8, 23.4, 14.5, 11.0, 31.9, 99.4, 77.2, 80.2, 100.1, 33.1, 32.6, 56.3, 55.8, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), gabriel_id, CURRENT_DATE - 2, 22, 76.5, 1.8, 23.61, 14.0, 10.7, 32.3, 99.5, 77.0, 80.0, 100.1, 33.2, 32.7, 56.4, 55.9, 'ACTIVE', 'Ganho consistente de massa muscular, ajustar carga dos treinos de push', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Camila
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), camila_id, CURRENT_DATE - 60, 35, 58.0, 1.6, 22.66, 27.0, 15.7, 24.4, 96.7, 83.5, 86.5, 98.6, 31.0, 30.5, 54.4, 53.9, 'SEDENTARY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), camila_id, CURRENT_DATE - 30, 35, 58.0, 1.6, 22.66, 27.0, 15.7, 24.4, 96.7, 83.5, 86.5, 98.6, 31.0, 30.5, 54.4, 53.9, 'SEDENTARY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), camila_id, CURRENT_DATE - 2, 35, 58.0, 1.6, 22.66, 27.0, 15.7, 24.5, 96.7, 83.5, 86.5, 98.6, 31.0, 30.5, 54.4, 53.9, 'SEDENTARY', 'Recuperação de lesão no joelho, plano pausado até liberação médica', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Amanda
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), amanda_id, CURRENT_DATE - 60, 26, 74.0, 1.68, 26.22, 30.0, 22.2, 31.1, 99.1, 85.0, 88.0, 99.9, 32.9, 32.4, 56.1, 55.6, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), amanda_id, CURRENT_DATE - 30, 26, 71.6, 1.68, 25.37, 27.5, 19.7, 30.1, 98.7, 83.8, 86.8, 99.7, 32.6, 32.1, 55.9, 55.4, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), amanda_id, CURRENT_DATE - 2, 26, 69.4, 1.68, 24.59, 25.2, 17.5, 29.3, 98.4, 82.6, 85.6, 99.6, 32.3, 31.8, 55.6, 55.1, 'MODERATE', 'Excelente resposta ao treino funcional, perda de peso consistente', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Diego
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), diego_id, CURRENT_DATE - 60, 27, 70.0, 1.75, 22.86, 20.0, 14.0, 29.4, 98.5, 80.0, 83.0, 99.6, 32.4, 31.9, 55.7, 55.2, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), diego_id, CURRENT_DATE - 30, 27, 70.8, 1.75, 23.12, 19.4, 13.7, 29.8, 98.6, 79.7, 82.7, 99.7, 32.5, 32.0, 55.8, 55.3, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), diego_id, CURRENT_DATE - 2, 27, 71.5, 1.75, 23.35, 18.8, 13.4, 30.2, 98.7, 79.4, 82.4, 99.7, 32.6, 32.1, 55.9, 55.4, 'MODERATE', 'Bom progresso para iniciante, aumentar carga gradualmente', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Juliana
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), juliana_id, CURRENT_DATE - 60, 31, 68.0, 1.62, 25.91, 33.0, 22.4, 28.6, 98.2, 86.5, 89.5, 99.4, 32.2, 31.7, 55.5, 55.0, 'LIGHT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), juliana_id, CURRENT_DATE - 30, 31, 66.0, 1.62, 25.15, 31.0, 20.5, 27.8, 97.9, 85.5, 88.5, 99.3, 31.9, 31.4, 55.3, 54.8, 'LIGHT', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), juliana_id, CURRENT_DATE - 2, 31, 64.1, 1.62, 24.42, 29.1, 18.7, 27.1, 97.6, 84.5, 87.5, 99.1, 31.7, 31.2, 55.1, 54.6, 'LIGHT', 'Excelente evolução no emagrecimento, seguir protocolo atual', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Felipe
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), felipe_id, CURRENT_DATE - 60, 25, 79.0, 1.77, 25.22, 17.0, 13.4, 33.2, 99.8, 78.5, 81.5, 100.3, 33.5, 33.0, 56.7, 56.2, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), felipe_id, CURRENT_DATE - 30, 25, 79.9, 1.77, 25.5, 16.5, 13.2, 33.6, 100.0, 78.2, 81.2, 100.4, 33.6, 33.1, 56.8, 56.3, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), felipe_id, CURRENT_DATE - 2, 25, 80.7, 1.77, 25.76, 16.0, 12.9, 34.1, 100.1, 78.0, 81.0, 100.5, 33.7, 33.2, 56.9, 56.4, 'ACTIVE', 'Braços evoluindo bem, priorizar tríceps na próxima fase', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Carolina
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), carolina_id, CURRENT_DATE - 60, 30, 65.0, 1.64, 24.17, 28.0, 18.2, 27.3, 97.8, 84.0, 87.0, 99.2, 31.8, 31.3, 55.1, 54.6, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), carolina_id, CURRENT_DATE - 30, 30, 63.8, 1.64, 23.72, 26.5, 16.9, 26.9, 97.6, 83.2, 86.2, 99.1, 31.7, 31.2, 55.0, 54.5, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), carolina_id, CURRENT_DATE - 2, 30, 62.7, 1.64, 23.31, 25.1, 15.7, 26.5, 97.4, 82.5, 85.5, 99.0, 31.5, 31.0, 54.9, 54.4, 'MODERATE', 'Cintura reduzindo conforme planejado, manter core 2x/semana', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Eduardo
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), eduardo_id, CURRENT_DATE - 60, 29, 88.0, 1.83, 26.28, 19.0, 16.7, 37.0, 101.2, 79.5, 82.5, 101.0, 34.6, 34.1, 57.7, 57.2, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), eduardo_id, CURRENT_DATE - 30, 29, 88.6, 1.83, 26.46, 18.6, 16.5, 37.3, 101.3, 79.3, 82.3, 101.1, 34.6, 34.1, 57.7, 57.2, 'ACTIVE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), eduardo_id, CURRENT_DATE - 2, 29, 89.2, 1.83, 26.64, 18.2, 16.2, 37.6, 101.4, 79.1, 82.1, 101.1, 34.7, 34.2, 57.8, 57.3, 'ACTIVE', 'Força máxima em ascensão, próxima avaliação com teste de 1RM', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Renata
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), renata_id, CURRENT_DATE - 60, 38, 71.0, 1.66, 25.77, 29.0, 20.6, 29.8, 98.7, 84.5, 87.5, 99.7, 32.5, 32.0, 55.8, 55.3, 'SEDENTARY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), renata_id, CURRENT_DATE - 30, 38, 70.5, 1.66, 25.58, 28.5, 20.1, 29.7, 98.6, 84.2, 87.2, 99.6, 32.5, 32.0, 55.8, 55.3, 'SEDENTARY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), renata_id, CURRENT_DATE - 2, 38, 70.0, 1.66, 25.4, 28.0, 19.6, 29.5, 98.5, 84.0, 87.0, 99.6, 32.4, 31.9, 55.7, 55.2, 'SEDENTARY', 'Frequência de treino melhorando, foco em consistência', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Vinicius
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), vinicius_id, CURRENT_DATE - 60, 24, 73.0, 1.79, 22.78, 18.0, 13.1, 30.7, 99.0, 79.0, 82.0, 99.8, 32.8, 32.3, 56.0, 55.5, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), vinicius_id, CURRENT_DATE - 30, 24, 73.8, 1.79, 23.03, 17.5, 12.9, 31.1, 99.1, 78.8, 81.8, 99.9, 32.9, 32.4, 56.1, 55.6, 'MODERATE', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), vinicius_id, CURRENT_DATE - 2, 24, 74.5, 1.79, 23.25, 17.0, 12.7, 31.4, 99.2, 78.5, 81.5, 100.0, 32.9, 32.4, 56.2, 55.7, 'MODERATE', 'Massa magra em evolução constante', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

    -- Isabela
    INSERT INTO physical_assessments (id, trainee_id, registered_at, age, weight, height, imc, body_fat, fat_mass, muscle_mass, chest, waist, abdomen, hip, right_arm, left_arm, right_thigh, left_thigh, activity_level, notes, front_view_photo_key, front_view_photo_file_id, back_view_photo_key, back_view_photo_file_id, left_view_photo_key, left_view_photo_file_id, right_view_photo_key, right_view_photo_file_id) VALUES
        (gen_random_uuid(), isabela_id, CURRENT_DATE - 60, 40, 66.0, 1.63, 24.84, 27.0, 17.8, 27.7, 97.9, 83.5, 86.5, 99.3, 31.9, 31.4, 55.3, 54.8, 'SEDENTARY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), isabela_id, CURRENT_DATE - 30, 40, 65.5, 1.63, 24.65, 26.5, 17.4, 27.6, 97.8, 83.2, 86.2, 99.2, 31.9, 31.4, 55.2, 54.7, 'SEDENTARY', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
        (gen_random_uuid(), isabela_id, CURRENT_DATE - 2, 40, 65.0, 1.63, 24.46, 26.0, 16.9, 27.4, 97.8, 83.0, 86.0, 99.2, 31.8, 31.3, 55.1, 54.6, 'SEDENTARY', 'Primeira fase de adaptação concluída com boa aderência', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

END $$;