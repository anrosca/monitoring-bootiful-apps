-- Users

--username: mike, password: shiba
insert into users (id, first_name, last_name, user_name, password, enabled, email_address) values ('5284f3d2-92d6-438a-8e24-949bd074662a', 'Mike', 'Smith', 'mike', '{bcrypt}$2a$10$alhEGyUE7CU46bO3B7oSH.tl0IygqaHCGvcCNBARm6ppgyURXK0rG', true, 'mike@gmail.com');

-- User roles
insert into user_roles(id, role_name, user_id) values ('6684f3d2-f2d6-d38a-ae24-c49bd07466ba', 'read', '5284f3d2-92d6-438a-8e24-949bd074662a');
insert into user_roles(id, role_name, user_id) values ('7684f3d2-f2d6-d38a-ae24-c49bd07466bb', 'write', '5284f3d2-92d6-438a-8e24-949bd074662a');

-- Investments
-- AAPL
insert into investments (id, name, user_id, created_at, symbol) values ('1da1f05d-344e-477c-b095-6529f0d756c1', 'Apple investment', '5284f3d2-92d6-438a-8e24-949bd074662a', '2021-04-29 11:12:13', 'AAPL');

-- TSLA
insert into investments (id, name, user_id, created_at, symbol) values ('1234f3d2-56f3-518a-5d24-949bd074669a', 'Tesla investment', '5284f3d2-92d6-438a-8e24-949bd074662a', '2021-04-27 14:15:16', 'TSLA');

-- Google
insert into investments (id, name, user_id, created_at, symbol) values ('5ddff3d2-56f3-518a-5d24-949bd074669b', 'Google investment', '5284f3d2-92d6-438a-8e24-949bd074662a', '2021-05-08 14:15:16', 'GOOGL');

-- Facebook
insert into investments (id, name, user_id, created_at, symbol) values ('6ddff3d2-56f3-518a-5d24-949bd07466bb', 'Facebook investment', '5284f3d2-92d6-438a-8e24-949bd074662a', '2021-05-09 14:15:16', 'FB');

-- Endava
insert into investments (id, name, user_id, created_at, symbol) values ('7ddff3d2-56f3-518a-5d24-949bd07466ab', 'Endava investment', '5284f3d2-92d6-438a-8e24-949bd074662a', '2021-05-02 14:15:16', 'DAVA');

--Amazon
insert into investments (id, name, user_id, created_at, symbol) values ('88dff3d2-56f3-518a-5d24-949bd07466ab', 'Amazon investment', '5284f3d2-92d6-438a-8e24-949bd074662a', '2021-05-21 14:15:16', 'AMZN');

--Netflix
insert into investments (id, name, user_id, created_at, symbol) values ('99dff3d2-56f3-518a-5d24-949bd07466ab', 'Netflix investment', '5284f3d2-92d6-438a-8e24-949bd074662a', '2021-05-20 14:15:16', 'NFLX');

-- Investment records
-- AAPL
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('26ef0589-8881-4194-a65e-2b731f80fbc7', 1, '2021-04-29 11:12:13', 130, 'AAPL', 130, '1da1f05d-344e-477c-b095-6529f0d756c1');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('33ef0589-4481-5594-a65e-66731f80fb99', 1, '2021-05-04 11:12:13', 131.5, 'AAPL', 131.5, '1da1f05d-344e-477c-b095-6529f0d756c1');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('11ef0589-1181-1194-a65e-11731f11fb11', 1, '2021-05-13 12:12:13', 100, 'AAPL', 100, '1da1f05d-344e-477c-b095-6529f0d756c1');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('21ef0589-1181-1194-a65e-11731f11fb11', 1, '2021-05-14 12:12:13', 98.3, 'AAPL', 98.3, '1da1f05d-344e-477c-b095-6529f0d756c1');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('31ef0589-1181-1194-a65e-11731f11fb11', 2, '2021-06-21 12:12:13', 320.0, 'AAPL', 160.0, '1da1f05d-344e-477c-b095-6529f0d756c1');

-- TSLA
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('5412f3d2-11f3-231a-5d24-34dfd074666d', 1, '2021-04-28 14:15:16', 54, 'TSLA', 54, '1234f3d2-56f3-518a-5d24-949bd074669a');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('9999f3d2-11f3-231a-5d24-34dfd074666d', 1, '2021-04-27 14:15:16', 300, 'TSLA', 300, '1234f3d2-56f3-518a-5d24-949bd074669a');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('8888f3d2-11f3-231a-5d24-34dfd074555d', 1, '2021-05-13 14:15:16', 720, 'TSLA', 720, '1234f3d2-56f3-518a-5d24-949bd074669a');

-- Google
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('addff3d2-a6f3-a18a-ad24-a49bd074669a', 0.5, '2021-05-08 14:15:16', 1369.13, 'GOOGL', 2738.26, '5ddff3d2-56f3-518a-5d24-949bd074669b');

-- Facebook
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('addff3d2-a6f3-a18a-5d24-949bd07466ba', 0.5, '2021-05-09 14:15:16', 180.75, 'FB', 361.5, '6ddff3d2-56f3-518a-5d24-949bd07466bb');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('bddff3d4-a6f3-a18a-5d24-949bd074ccba', 1, '2021-05-09 15:15:16', 370, 'FB', 370, '6ddff3d2-56f3-518a-5d24-949bd07466bb');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('fddff3d4-a6f3-a18a-5d24-949bd074ccbf', 0.5, '2021-05-06 15:15:16', 5, 'FB', 10, '6ddff3d2-56f3-518a-5d24-949bd07466bb');

-- Endava
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('dddff3d2-ddf3-518a-5d2d-949bd07466ac', 1, '2021-05-02 14:15:16', 79.80, 'DAVA', 79.80, '7ddff3d2-56f3-518a-5d24-949bd07466ab');
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('addff3d2-ddf3-518a-5d2d-949bd07466ac', 2, '2021-05-14 14:15:16', 115, 'DAVA', 57.5, '7ddff3d2-56f3-518a-5d24-949bd07466ab');

-- Amazon
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('fddff3df-ddf3-f18a-fd2d-f49bf0f466ac', 1, '2021-05-21 14:15:16', 3341.87, 'AMZN', 3341.87, '88dff3d2-56f3-518a-5d24-949bd07466ab');

-- Netflix
insert into investment_records (id, amount_bought, investment_date, spent, symbol, unit_price, investment_id)
values ('eddff3df-eef3-f18a-fe2d-f49bf0f466ac', 1, '2021-05-20 14:15:16', 619.97, 'NFLX', 619.97, '99dff3d2-56f3-518a-5d24-949bd07466ab');
