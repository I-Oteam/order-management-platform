-- p_users 테이블 목데이터 추가
INSERT INTO p_users
    (created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, user_id, email, password, role, username, nickname)
VALUES
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'test1@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'CUSTOMER', 'testCustomer', 'nickname1'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'test2@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'OWNER', 'testOwner', 'nickname2'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, 'test3@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'MANAGER', 'testManager', 'nickname3'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, 'test4@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'MASTER', 'testMaster', 'nickname4');


-- p_review 테이블 목데이터 추가
INSERT INTO p_review
    (review_user_id, is_public, review_score, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, review_id, review_image_url, review_content)
VALUES
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, false, 5, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d18'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d15'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d14'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d16'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d64'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d54'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 3, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d32'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 3, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d19'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 2, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d67'::uuid, 'test', 'test'),
    ('d2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 1, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d13'::uuid, 'test', 'test');
