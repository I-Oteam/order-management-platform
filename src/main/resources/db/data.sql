-- p_users 테이블 목데이터 추가
INSERT INTO p_users
    (created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, user_id, email, password, role, username, nickname)
VALUES
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, 'test0@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'CUSTOMER', 'testCustomer0', 'nickname0'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'test1@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'CUSTOMER', 'testCustomer', 'nickname1'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'test2@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'OWNER', 'testOwner', 'nickname2'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e3'::uuid, 'test3@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'MANAGER', 'testManager', 'nickname3'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e4'::uuid, 'test4@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'MASTER', 'testMaster', 'nickname4'),
    (now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e5'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e5'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e5'::uuid, 'test5@example.com', '$2a$10$4eKGfDmoeH4VsoW9O908eOwiSD1Rkw161fVS2hAeQNHmhzlB9/xBa', 'OWNER', 'testOwner2', 'nickname5');


-- p_district 테이블 더미데이터 추가
INSERT INTO p_district
    (created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, district_id,
                        district_dong_name, district_sigungu_code, district_sigungu_name)
VALUES
    (now(), null, now(), 'd6df52e4-6ec8-4d46-b0c2-90f5f2624444'::uuid, null, 'd6df52e4-6ec8-4d46-b0c2-90f5f2624445'::uuid, 'd6df52e4-6ec8-4d46-b0c2-90f5f2624446'::uuid,
        '망원동' , '1234' , '서울특별시 마포구'),

    (now(), null, now(), 'd6df52e4-6ec8-4d46-b0c2-90f5f2624454'::uuid, null, 'd6df52e4-6ec8-4d46-b0c2-90f5f2624455'::uuid, 'd6df52e4-6ec8-4d46-b0c2-90f5f2624456'::uuid,
        '합정동' , '1234' , '서울특별시 마포구'),

    (now(), null, now(), 'd6df52e4-6ec8-4d46-b0c2-90f5f2624554'::uuid, null, 'd6df52e4-6ec8-4d46-b0c2-90f5f2624655'::uuid, 'd6df52e4-6ec8-4d46-b0c2-90f5f2624756'::uuid,
        '한남동' , '1234' , '서울특별시 마포구');


-- p_restaurant_category 테이블 더미데이터 추가
insert into p_restaurant_category
    (created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, rc_id, rc_name)
values
    (now(),null,now(), 'd78dfdc2-edc6-4bec-f158-a7e055dcfc71'::uuid, null, 'd78dfdc2-edc6-4bec-f158-a7e055dcfc72'::uuid, 'd78dfdc2-edc6-4bec-f158-a7e055dcfc73'::uuid, '한식' ),
    (now(),null,now(), 'd78dfdc2-edc6-4bec-f158-a7e055dcfc71'::uuid, null, 'd78dfdc2-edc6-4bec-f158-a7e055dcfc72'::uuid, 'd78dfdc2-edc6-4bec-f158-a7e055dcfc74'::uuid, '양식' );


-- p_restaurant 테이블 목데이터 추가
INSERT INTO p_restaurant
    (created_at, modified_at, created_by, modified_by, res_id, res_phone, res_address, res_name, res_image_url, res_owner_id, res_category_id, res_district_id)
VALUES
    (now(), now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '02-0000-0000', '서울시 스파르타 스파르타동 스번지', '스파르타파스타', null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, 'd78dfdc2-edc6-4bec-f158-a7e055dcfc74'::uuid, 'd6df52e4-6ec8-4d46-b0c2-90f5f2624446'::uuid);

insert into p_restaurant
    (created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, res_category_id,
                          res_district_id, res_id, res_owner_id, res_phone, res_address, res_name, res_image_url)
values
    (now(),null,now(),'768f5a3b-e6d8-46d6-f953-b9c425ab1cf9'::uuid, null, '768f5a3b-e6d8-46d6-f953-b9c425ab1cf0'::uuid,'d78dfdc2-edc6-4bec-f158-a7e055dcfc73'::uuid,
        'd6df52e4-6ec8-4d46-b0c2-90f5f2624446'::uuid,'768f5a3b-e6d8-46d6-f953-b9c425ab1cf1'::uuid , 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '010-1234-5678' ,
        '재현건물1층1호', '건물주', null);


-- p_restaurant_score 테이블 목데이터 추가
INSERT INTO p_restaurant_score
    (rs_score, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, rs_id, rs_res_id)
VALUES
    (3.4, now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '231cee45-79fb-4553-b109-934e607bb058'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid),
    (0,now(), NULL, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce121e2'::uuid, NULL, 'd2ed72d8-090a-4efb-abe4-7acbdce122e2'::uuid, '231cee45-79fb-4553-b109-934e607bb059'::uuid, '768f5a3b-e6d8-46d6-f953-b9c425ab1cf1'::uuid);


-- p_restaurant_menu 테이블 목데이터 추가
INSERT INTO p_restaurant_menu
    (is_public, rm_price, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, res_id, rm_id, rm_description, rm_name, rm_image_url, rm_status)
VALUES
    (true, 7500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d5'::uuid, '맛있는 열정 가득 파스타입니다.', '열파르타', 'imageurl', 'ON_SALE'),
    (true, 8500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d6'::uuid, '맛있는 열정 가득 열리오올리오입니다.', '열리오올리오', 'imageurl', 'ON_SALE'),
    (true, 9000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d7'::uuid, '겉바속촉 고등어 구이와 푸짐한 파스타 곁들임!  참김에 싸먹는 별미 정식.  스파르타파스타만의 특별한 조합', '고등어 구이 정식', '고등어_사진.jpg', 'ON_SALE'),
    (true, 10500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d8'::uuid, '여수산 갓김치의 톡 쏘는 매콤함과 쫄깃한 면발의 환상적인 만남!  스파르타파스타만의 특별한 비빔국수','킹갓제너럴 비빔국수', '갓비빔국수.jpg', 'SOLD_OUT'),
    (true, 12000, now(), null, now(), 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, null, 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'a1b2c3d4-5678-90ab-cdef-1234567890aa'::uuid, '바삭한 튀김과 달콤 짭조름한 돈가츠 소스의 조화! 정통 일본식 돈가츠 정식', '일본식 돈가츠 정식', '돈가츠.jpg', 'ON_SALE'),
    (true, 15000, now(), null, now(), 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, null, 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'a1b2c3d4-5678-90ab-cdef-1234567890bb'::uuid, '진한 돈코츠 육수와 쫄깃한 면발이 조화를 이루는 일본 정통 라멘', '돈코츠 라멘', '돈코츠라멘.jpg', 'ON_SALE'),
    (true, 13000, now(), null, now(), 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, null, 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'a1b2c3d4-5678-90ab-cdef-1234567890cc'::uuid, '신선한 해산물이 듬뿍 들어간 일본 전통 해산물 덮밥', '카이센동', '카이센동.jpg', 'ON_SALE'),
    (true, 11000, now(), null, now(), 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, null, 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'a1b2c3d4-5678-90ab-cdef-1234567890dd'::uuid, '부드럽고 진한 달걀 소스가 매력적인 일본식 오믈렛 덮밥', '오야코동', '오야코동.jpg', 'ON_SALE'),
    (true, 12500, now(), null, now(), 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, null, 'e3f1a2b4-7d33-4c8e-ae72-9f5dbe7621a3'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'a1b2c3d4-5678-90ab-cdef-1234567890ee'::uuid, '짭조름한 소스와 함께 즐기는 일본 전통 메밀국수', '자루 소바', '자루소바.jpg', 'ON_SALE'),
    (true, 10500, now(), null, now(), 'f4d5e6b7-8c99-4a0d-bb11-2e3f456789aa'::uuid, null, 'f4d5e6b7-8c99-4a0d-bb11-2e3f456789aa'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'b7c8d9e0-1234-5678-90ab-cdef12345678'::uuid, '고소한 크림과 신선한 버섯이 어우러진 크림 파스타', '버섯 크림 파스타', 'mushroom_pasta.jpg', 'ON_SALE'),
    (true, 13500, now(), null, now(), 'f4d5e6b7-8c99-4a0d-bb11-2e3f456789aa'::uuid, null, 'f4d5e6b7-8c99-4a0d-bb11-2e3f456789aa'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'c9d0e1f2-2345-6789-0abc-def123456789'::uuid, '매콤한 소스와 부드러운 치즈가 조화로운 한입 가득 라자냐', '스파이시 치즈 라자냐', 'spicy_lasagna.jpg', 'HIDDEN'),
    (true, 15500, now(), null, now(), 'f4d5e6b7-8c99-4a0d-bb11-2e3f456789aa'::uuid, null, 'f4d5e6b7-8c99-4a0d-bb11-2e3f456789aa'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd1e2f3a4-3456-7890-abcd-ef1234567890'::uuid, '달콤한 데리야끼 소스와 부드러운 닭고기가 어우러진 일본식 덮밥', '데리야끼 치킨 덮밥', 'teriyaki_chicken.jpg', 'SOLD_OUT'),
    (false, 1000000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e2'::uuid, '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, '2c48e6c8-56c5-47f9-bd6f-af01647a0d1b'::uuid, '매니저랑 가게 오너만 볼 수 있는 메뉴입니다.', '우주 최강 맛있는 씨크릿 화덕 피자 레시피', 'pizza', 'HIDDEN');


-- p_order 테이블 목데이터 추가
INSERT INTO p_order
    (order_res_total, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, order_id, order_location, order_res_id, order_user_id, order_request, order_status, order_type)
VALUES
    (25500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de1'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (25500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de2'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de3'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de4'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de5'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de6'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de7'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de8'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de9'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de0'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036d11'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'COMPLETED', 'DELIVERY'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036d12'::uuid, '주문 배달지 주소', '3fa85f64-5717-4562-b3fc-2c963f66afa6'::uuid, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, '주문에 대한 요청사항', 'WAITING', 'DELIVERY');

-- p_order_menu 테이블 목데이터 추가
INSERT INTO p_order_menu
    (order_count, order_price, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, order_id, order_menu_id, order_rm_id)
VALUES
    (1, 8500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de1'::uuid, 'd8beeaac-9314-4e76-811a-edcc201fed30'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d5'::uuid),
    (2, 8500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de1'::uuid, 'd8beeaac-9314-4e76-811a-edcc201fed31'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d6'::uuid),
    (1, 8500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de2'::uuid, 'd8beeaac-9314-4e76-811a-edcc201fed32'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d5'::uuid),
    (2, 8500, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e0'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de2'::uuid, 'd8beeaac-9314-4e76-811a-edcc201fed33'::uuid, '439f222b-0cbb-4600-a989-e7fdabf120d6'::uuid);


-- p_payment 테이블 목데이터 추가
INSERT INTO p_payment
    (payment_total, created_at, deleted_at, modified_at, created_by, deleted_by, modified_by, payment_id, payment_order_id, payment_method, payment_number, payment_status)
VALUES
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de1'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de1'::uuid, 'CREDIT_CARD', '4111111111111111', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de2'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de2'::uuid, 'KAKAO_PAY', '1234567890123456', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de3'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de3'::uuid, 'BANK_TRANSFER', '9876543210987654', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de4'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de4'::uuid, 'CREDIT_CARD', '4222222222222222', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de5'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de5'::uuid, 'PAYCO', '5678901234567890', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de6'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de6'::uuid, 'NAVER_PAY', '1111222233334444', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de7'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de7'::uuid, 'CREDIT_CARD', '4333333333333333', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de8'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de8'::uuid, 'KAKAO_PAY', '2222333344445555', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de9'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de9'::uuid, 'BANK_TRANSFER', '6666777788889999', 'PENDING'),
    (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036de0'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036de0'::uuid, 'CREDIT_CARD', '4444555566667777', 'PENDING');
    -- (100000, now(), null, now(), 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, null, 'd2ed72d8-090a-4efb-abe4-7acbdce120e1'::uuid, 'a1f5ca7b-2c3d-49bb-9c6d-425c85036d11'::uuid, 'd8ef5ca7-2b3c-49bb-9c6d-425c85036d11'::uuid, 'PAYCO', '8888999900001111', 'PENDING');


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

