INSERT INTO `aaa_reservation_state` VALUES ('RESERVED');
INSERT INTO `aaa_aircraft_type` VALUES ('737'),('A-380'),('CNA');
INSERT INTO `aaa_aircraft` VALUES ('D-LIMA','737'),('A-ANFI','A-380'),('C-ANDS','CNA');
INSERT INTO `aaa_pilot` VALUES ('iens','irina.ens@invalid.domain','Irina Ens','$1$aaa$X9IIyn1EGtKsXyvzxK0cp.'),('sherdt','sergej.herdt@prodyna.com','Sergej Herdt','$1$aaa$X9IIyn1EGtKsXyvzxK0cp.');
INSERT INTO `aaa_license` VALUES ('26f25f9b-d6ba-49b8-a0b5-95278e4e51df','2014-07-31 00:00:00','737','sherdt'),('ab3df39b-c455-42e4-8820-5ecacb700308','2014-07-31 00:00:00','A-380','iens');
INSERT INTO `aaa_reservation` VALUES ('6c1b038a-c26e-45d8-a5e5-688358c883f3','2014-07-16 13:00:00','2014-07-16 09:20:00','D-LIMA','RESERVED','sherdt'),('f9a7fff6-b633-40f1-b4bf-d3bf9d59b14f','2014-07-17 15:00:00','2014-07-17 10:30:00','A-ANFI','RESERVED','iens');
