-- p_review 테이블 목데이터 추가
INSERT INTO p_review
    (is_public, review_score, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, review_id, review_image_url, review_content)
VALUES
    (true, 5, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d18'::uuid, 'test', 'test'),
    (true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e5'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d15'::uuid, 'test', 'test'),
    (true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e6'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d14'::uuid, 'test', 'test'),
    (true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e6'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d16'::uuid, 'test', 'test'),
    (true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e6'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d64'::uuid, 'test', 'test'),
    (true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e6'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d54'::uuid, 'test', 'test'),
    (true, 3, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e6'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d32'::uuid, 'test', 'test'),
    (true, 3, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e6'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d19'::uuid, 'test', 'test'),
    (true, 2, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e6'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d67'::uuid, 'test', 'test'),
    (true, 1, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e7'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e5'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d13'::uuid, 'test', 'test');
