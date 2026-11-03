-- Compatível com a V8. Cada exercício é inserido individualmente.
-- As colunas photo_key, video_key, photo_file_id e video_file_id são anuláveis
-- e, portanto, não precisam constar nos INSERTs enquanto não houver mídia.

-- Exercícios do sistema (system = TRUE)
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Supino Reto com Barra', TRUE, TRUE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Supino Inclinado com Halteres', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Supino Declinado com Barra', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Crucifixo Reto com Halteres', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Crossover no Cabo', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Peck Deck (Voador)', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Flexão de Braço', TRUE, FALSE);

INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Puxada Frontal (Pulley)', TRUE, TRUE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Puxada Atrás da Nuca', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Remada Curvada com Barra', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Remada Baixa no Cabo', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Remada Unilateral com Halter', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Barra Fixa (Pull-up)', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Levantamento Terra', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Hiperextensão Lombar (Banco Romano)', TRUE, FALSE);

INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Encolhimento de Ombros com Halteres', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Remada Alta', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Desenvolvimento com Halteres', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Desenvolvimento Militar com Barra', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Elevação Lateral', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Elevação Frontal', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Crucifixo Invertido (Posterior de Ombro)', TRUE, FALSE);

INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Rosca Direta com Barra', TRUE, TRUE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Rosca Alternada com Halteres', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Rosca Martelo', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Rosca Scott', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Tríceps Corda (Pulley)', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Tríceps Testa', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Tríceps Francês', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Supino Fechado', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Mergulho em Paralelas (Dips)', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Rosca de Punho', TRUE, FALSE);

INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Abdominal Supra', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Abdominal Infra', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Abdominal Oblíquo', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Prancha Isométrica', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Elevação de Pernas na Barra', TRUE, FALSE);

INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Agachamento Livre', TRUE, TRUE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Leg Press 45°', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Cadeira Extensora', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Agachamento Búlgaro', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Afundo (Passada)', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Mesa Flexora', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Cadeira Flexora', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Stiff (Levantamento Terra Romeno)', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Elevação Pélvica (Hip Thrust)', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Agachamento Sumô', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Cadeira Adutora', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Cadeira Abdutora', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Coice na Polia (Glute Kickback)', TRUE, FALSE);

INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Elevação de Panturrilha em Pé', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Elevação de Panturrilha Sentado', TRUE, FALSE);
INSERT INTO exercises (id, name, system, favorite) VALUES (gen_random_uuid(), 'Panturrilha no Leg Press', TRUE, FALSE);