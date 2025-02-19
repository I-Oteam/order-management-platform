-- p_users 테이블 목데이터 추가
INSERT INTO p_users
    (created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, user_id, email, password, role, username, nickname)
VALUES
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'test1@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'CUSTOMER', 'testCustomer', 'nickname1'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'test2@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'OWNER', 'testOwner', 'nickname2'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, 'test3@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'MANAGER', 'testManager', 'nickname3'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, 'test4@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'MASTER', 'testMaster', 'nickname4');

-- p_restaurant 테이블 목데이터 추가
INSERT INTO p_restaurant (created_at, modified_at, created_by, modified_by, res_id, res_phone, res_address, res_name, res_image_url, res_owner_id)
VALUES (now(), now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '02-0000-0000', '서울시 스파르타 스파르타동 스번지', '스파르타파스타', null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid);

-- p_restaurant_menu 테이블 목데이터 추가
INSERT INTO p_restaurant_menu (is_public, rm_price, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, res_id, rm_id, rm_description, rm_name, rm_image_url)
VALUES (true, 8500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d5'::uuid, '맛있는 열정 가득 파스타입니다.', '열파르타', null);

-- p_order 테이블 목데이터 추가
INSERT INTO p_order
    (order_res_total, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, order_id, order_location, order_res_id, order_user_id, order_request, order_status, order_type)
VALUES
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de1'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de2'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de3'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de4'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de5'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de6'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de7'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de8'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de9'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de0'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036d11'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY');


-- p_review 테이블 목데이터 추가
INSERT INTO p_review
    (review_order_id, review_res_id, review_user_id, is_public, review_score, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, review_id, review_image_url, review_content)
VALUES
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de1'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, false, 5, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d18'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d15'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de3'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d14'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de4'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d16'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de5'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d64'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de6'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d54'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de7'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 3, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d32'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de8'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 3, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d19'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de9'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 2, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d67'::uuid, 'test', 'test'),
    ('d8ef5ca7-2b3c-49bb-9c6d-425c85036de0'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, true, 1, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '1c114e8f-ccd0-42b8-a7fa-67fee61d4d13'::uuid, 'test', 'test');

