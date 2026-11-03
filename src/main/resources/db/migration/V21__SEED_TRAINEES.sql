ç-- 16 alunos de teste (username/password iguais ao primeiro nome, em minúsculas),
-- distribuídos entre os 4 personal trainers: Lucas(5), Fernanda(3), Rafael(7), Patrícia(1).
-- avatar_url fica NULL propositalmente em alguns (Bruno e Renata) para simular
-- o caso de aluno sem foto cadastrada (o front-end deve exibir um avatar padrão).

DO $$
DECLARE
    mateus_user_id   UUID := gen_random_uuid();
    beatriz_user_id  UUID := gen_random_uuid();
    thiago_user_id   UUID := gen_random_uuid();
    larissa_user_id  UUID := gen_random_uuid();
    bruno_user_id    UUID := gen_random_uuid();

    gabriel_user_id  UUID := gen_random_uuid();
    camila_user_id   UUID := gen_random_uuid();
    amanda_user_id   UUID := gen_random_uuid();

    diego_user_id    UUID := gen_random_uuid();
    juliana_user_id  UUID := gen_random_uuid();
    felipe_user_id   UUID := gen_random_uuid();
    carolina_user_id UUID := gen_random_uuid();
    eduardo_user_id  UUID := gen_random_uuid();
    renata_user_id   UUID := gen_random_uuid();
    vinicius_user_id UUID := gen_random_uuid();

    isabela_user_id  UUID := gen_random_uuid();

    lucas_pt_id    UUID;
    fernanda_pt_id UUID;
    rafael_pt_id   UUID;
    patricia_pt_id UUID;
BEGIN
    SELECT id INTO lucas_pt_id    FROM personals_trainers WHERE first_name = 'Lucas';
    SELECT id INTO fernanda_pt_id FROM personals_trainers WHERE first_name = 'Fernanda';
    SELECT id INTO rafael_pt_id   FROM personals_trainers WHERE first_name = 'Rafael';
    SELECT id INTO patricia_pt_id FROM personals_trainers WHERE first_name = 'Patrícia';

    -- Usuários
    INSERT INTO users (id, username, password, full_name, email, recovery_email, account_non_expired, account_non_locked, credentials_non_expired, enabled) VALUES
        (mateus_user_id,   'mateus',   '{pbkdf2}775b3343f2d48858b89d939e12f2079061d66503f8951f0d3acb5e0b81424a8cf6bd176f9dba7d6f', 'Mateus',          'mateus@corefitpro.com',   'mateus@corefitpro.com',   TRUE, TRUE, TRUE, TRUE),
        (beatriz_user_id,  'beatriz',  '{pbkdf2}bf10b13a9a51941ec065e736f9dcbcd5acba57fa502a3b6e7129e18de4e609efddb646480abbe709', 'Beatriz Lima',    'beatriz@corefitpro.com',  'beatriz@corefitpro.com',  TRUE, TRUE, TRUE, TRUE),
        (thiago_user_id,   'thiago',   '{pbkdf2}ab9d2c13c4922af1eafc049651712b29025e98be5dafee40b5b6722ddae349fa72d8e741841cdb62', 'Thiago Ramos',    'thiago@corefitpro.com',   'thiago@corefitpro.com',   TRUE, TRUE, TRUE, TRUE),
        (larissa_user_id,  'larissa',  '{pbkdf2}be4a4bec454cf2bfe6bd85e3b062f26e20305dcfa96c04b50184eaba8859c9962894b0d169d347dd', 'Larissa Fontes',  'larissa@corefitpro.com',  'larissa@corefitpro.com',  TRUE, TRUE, TRUE, TRUE),
        (bruno_user_id,    'bruno',    '{pbkdf2}9e2ef9d9fd32064215f33fdbe884fe1081e7650e780ca0fd4affabdeb7cc5714d33ecb842fa1d4bb', 'Bruno Cardoso',   'bruno@corefitpro.com',    'bruno@corefitpro.com',    TRUE, TRUE, TRUE, TRUE),

        (gabriel_user_id,  'gabriel',  '{pbkdf2}60997dfbd82e32a385b85fecb59af6ec4358cb5fef0ef588632851cde5796dafa0e52064e8cdd187', 'Gabriel Santos',  'gabriel@corefitpro.com',  'gabriel@corefitpro.com',  TRUE, TRUE, TRUE, TRUE),
        (camila_user_id,   'camila',   '{pbkdf2}ed4d4e75f2a30a45be82844099619d7101285d734478125556a074799a8ce3978f23001930037a89', 'Camila Rocha',    'camila@corefitpro.com',   'camila@corefitpro.com',   TRUE, TRUE, TRUE, TRUE),
        (amanda_user_id,   'amanda',   '{pbkdf2}d3d256385c18e11c6c6793d6a77bfc1d62a9b7cfae294a5854a53a5caaa155613864589aef12a834', 'Amanda Teixeira', 'amanda@corefitpro.com',   'amanda@corefitpro.com',   TRUE, TRUE, TRUE, TRUE),

        (diego_user_id,    'diego',    '{pbkdf2}d024b062d805fe965d85ce91dd957c78832c82d0aa57d8098fa0d79ad316d7f3c9fc2135c4bf7b70', 'Diego Alves',     'diego@corefitpro.com',    'diego@corefitpro.com',    TRUE, TRUE, TRUE, TRUE),
        (juliana_user_id,  'juliana',  '{pbkdf2}1bd5980b539c48a9780d11d817ffe0ca7405d8dc331167f246fb116f2c5c9f6318973213855f4299', 'Juliana Pereira', 'juliana@corefitpro.com',  'juliana@corefitpro.com',  TRUE, TRUE, TRUE, TRUE),
        (felipe_user_id,   'felipe',   '{pbkdf2}0871db1ccc8199908b92726e01cecf7e8d1e826c6dd6409090e9d45ff78036a636218818a99e81d4', 'Felipe Nogueira', 'felipe@corefitpro.com',   'felipe@corefitpro.com',   TRUE, TRUE, TRUE, TRUE),
        (carolina_user_id, 'carolina', '{pbkdf2}dfb8119f13a5d9c3cad9626d2c35616716aa786fae94ecebb100ed870e5e2600229f0f79fdcab268', 'Carolina Duarte', 'carolina@corefitpro.com', 'carolina@corefitpro.com', TRUE, TRUE, TRUE, TRUE),
        (eduardo_user_id,  'eduardo',  '{pbkdf2}ebf4ddb4a3838c2524caf319a6425e0bd8e9dcd0b30694bb7725099b6356b9a8fb69244edd84df15', 'Eduardo Martins', 'eduardo@corefitpro.com',  'eduardo@corefitpro.com',  TRUE, TRUE, TRUE, TRUE),
        (renata_user_id,   'renata',   '{pbkdf2}c80bfdb73a78e31ab89bf693b521710816c31bb696268dbea3dfb74f5ef8cb67f88a22f8e1cf8e62', 'Renata Vieira',   'renata@corefitpro.com',   'renata@corefitpro.com',   TRUE, TRUE, TRUE, TRUE),
        (vinicius_user_id, 'vinicius', '{pbkdf2}39ca6db71e4437b7be9f00322f9ddd490565066b74018b17be81414b975a4524999a4c4a343eaf28', 'Vinícius Barros', 'vinicius@corefitpro.com', 'vinicius@corefitpro.com', TRUE, TRUE, TRUE, TRUE),

        (isabela_user_id,  'isabela',  '{pbkdf2}7e15129a0fa3cb1a8cb26f34574f665447e594c8e79d85d5442462748d131f3e62632c49f81b1d5d', 'Isabela Campos',  'isabela@corefitpro.com',  'isabela@corefitpro.com',  TRUE, TRUE, TRUE, TRUE);

    -- Role TRAINEE para todos
    INSERT INTO user_role (user_id, role_id)
    SELECT u.id, r.id FROM users u, roles r
    WHERE u.id IN (
        mateus_user_id, beatriz_user_id, thiago_user_id, larissa_user_id, bruno_user_id,
        gabriel_user_id, camila_user_id, amanda_user_id,
        diego_user_id, juliana_user_id, felipe_user_id, carolina_user_id, eduardo_user_id, renata_user_id, vinicius_user_id,
        isabela_user_id
    ) AND r.name = 'TRAINEE';

    -- Trainees (avatar_key/avatar_file_id NULL para todos: nenhum tem avatar enviado ao B2 ainda,
    -- o front-end deve exibir um avatar padrão nesse caso)
    INSERT INTO trainees (id, first_name, last_name, avatar_key, avatar_file_id, user_id, personal_trainer_id) VALUES
        -- Lucas (5)
        (gen_random_uuid(), 'Mateus',   '',          NULL, NULL, mateus_user_id,   lucas_pt_id),
        (gen_random_uuid(), 'Beatriz',  'Lima',      NULL, NULL, beatriz_user_id,  lucas_pt_id),
        (gen_random_uuid(), 'Thiago',   'Ramos',     NULL, NULL, thiago_user_id,   lucas_pt_id),
        (gen_random_uuid(), 'Larissa',  'Fontes',    NULL, NULL, larissa_user_id,  lucas_pt_id),
        (gen_random_uuid(), 'Bruno',    'Cardoso',   NULL, NULL,                                                                                            bruno_user_id,    lucas_pt_id),

        -- Fernanda (3)
        (gen_random_uuid(), 'Gabriel',  'Santos',    NULL, NULL, gabriel_user_id,  fernanda_pt_id),
        (gen_random_uuid(), 'Camila',   'Rocha',     NULL, NULL, camila_user_id,   fernanda_pt_id),
        (gen_random_uuid(), 'Amanda',   'Teixeira',  NULL, NULL, amanda_user_id,   fernanda_pt_id),

        -- Rafael (7)
        (gen_random_uuid(), 'Diego',    'Alves',     NULL, NULL, diego_user_id,    rafael_pt_id),
        (gen_random_uuid(), 'Juliana',  'Pereira',   NULL, NULL, juliana_user_id,  rafael_pt_id),
        (gen_random_uuid(), 'Felipe',   'Nogueira',  NULL, NULL, felipe_user_id,   rafael_pt_id),
        (gen_random_uuid(), 'Carolina', 'Duarte',    NULL, NULL, carolina_user_id, rafael_pt_id),
        (gen_random_uuid(), 'Eduardo',  'Martins',   NULL, NULL, eduardo_user_id,  rafael_pt_id),
        (gen_random_uuid(), 'Renata',   'Vieira',    NULL, NULL,                                                                                            renata_user_id,   rafael_pt_id),
        (gen_random_uuid(), 'Vinícius', 'Barros',    NULL, NULL, vinicius_user_id, rafael_pt_id),

        -- Patrícia (1)
        (gen_random_uuid(), 'Isabela',  'Campos',    NULL, NULL, isabela_user_id,  patricia_pt_id);
END $$;
