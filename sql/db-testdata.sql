USE restaurant;

-- login: admin_john_test 		pass: john 		role: admin
-- login: admin_robert_test 	pass: robert 	role: admin
-- login: eric_test 			pass: eric 		role: user
-- login: frank_test			pass: frank		role: user
-- login: jack_test				pass: jack		role: user
-- login: kyle_test				pass: kyle		role: user
INSERT INTO user (login, password, role_id) VALUES ('admin_john_test', 'B7FCC6E612145267D2FFEA04BE754A34128C1ED8133A09BFBBABD6AFE6327688AA71D47343DD36E719F35F30FA79AEC540E91B81C214FDDFE0BEDD53370DF46D', 2);
INSERT INTO user (login, password, role_id) VALUES ('admin_robert_test', '5A3A208F91F046DA16804B818B3B9E8612B0246EA5EFBB7207620A8B4CA026392FCF05AD37F1883F09DB46786711DBCC528483A0D6CC5E4D5EE18AF9414210CB', 2);
INSERT INTO user (login, password, role_id) VALUES ('eric_test', '7556DD30A844F3037DB12CE50B8FAA869D9A16A968F769C038764EB1BF304C3497237B7BA1123D8B3B5E3FA3FF8C0BD1EE9CBE4E6ECE1FDF12007C5B498F41DB', 1);
INSERT INTO user (login, password, role_id) VALUES ('frank_test', '19B702B6F1B135CA009CE1DF13D84738B74CB7445AF63DD36F2FDEFE77DF83FCDECF307DB536093528242C3CE52A9350455ACB1E8AD906FCCD40FD095ABAB4F1', 1);
INSERT INTO user (login, password, role_id) VALUES ('jack_test', '77C62FF676394D2E1962C6F7BE65EA23B5650A3E69359B4337F9DC4EA6165AFEC4787529E690708B1A8C9FB89FE105C151838A9EA235F7B1763982F6256F5B92', 1);
INSERT INTO user (login, password, role_id) VALUES ('kyle_test', 'B8372B7E02F83D0C6C2C54904C2BDBE8F212E3E24FA1A8FF45016AFDD971C02736BC49818C954FEB8393D98930C4B4996943D31D9C28D9D796ABEE6407A8999A', 1);

INSERT INTO receipt (user_id, status_id, total, manager_id) VALUES ();