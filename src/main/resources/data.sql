-- 1. Agences
INSERT IGNORE INTO agence (id_agence, nom, ville, adresse, telephone) VALUES
(1, 'AutoLoc Tunis Centre', 'Tunis', '15 Avenue Habib Bourguiba', '+216 71 123 456'),
(2, 'AutoLoc Ariana', 'Ariana', '42 Avenue Hédi Nouira', '+216 71 789 012'),
(3, 'AutoLoc Sousse Corniche', 'Sousse', 'Boulevard de la Corniche', '+216 73 345 678'),
(4, 'AutoLoc Sfax Centre', 'Sfax', 'Route de Téniour km 1', '+216 74 901 234');

-- 2. Equipements
INSERT IGNORE INTO equipement (id_equipement, libelle) VALUES
(1, 'GPS Navigation'),
(2, 'Siège Bébé'),
(3, 'Climatisation Bi-Zone'),
(4, 'Caméra de Recul'),
(5, 'Toit Ouvrant'),
(6, 'Régulateur de Vitesse Adaptatif');

-- 3. Employés
INSERT IGNORE INTO employe (id_employe, nom, prenom, role, id_agence) VALUES
(1, 'Ben Salah', 'Mohamed', 'MANAGER', 1),
(2, 'Trabelsi', 'Youssef', 'AGENT', 1),
(3, 'Gharbi', 'Amina', 'AGENT', 2),
(4, 'Bouazizi', 'Sonia', 'MANAGER', 3),
(5, 'Mejri', 'Walid', 'AGENT', 4);

-- 4. Clients
INSERT IGNORE INTO client (id_client, nom, prenom, email, telephone, num_permis, date_inscription) VALUES
(1, 'Degachi', 'Ahmed', 'ahmed.degachi@esprit.tn', '+216 98 111 222', 'TUN-12345678', '2024-01-15'),
(2, 'Ben Ali', 'Karim', 'karim.benali@gmail.com', '+216 55 333 444', 'TUN-23456789', '2024-02-20'),
(3, 'Mansouri', 'Fatma', 'fatma.mansouri@outlook.com', '+216 22 555 666', 'TUN-34567890', '2024-03-10'),
(4, 'Jaziri', 'Walid', 'walid.jaziri@yahoo.fr', '+216 29 777 888', 'TUN-45678901', '2024-04-05');

-- 5. Véhicules
INSERT IGNORE INTO vehicule (id_vehicule, immatriculation, marque, modele, categorie, tarif_journalier, statut, id_agence) VALUES
(1, '220 TUN 1001', 'Volkswagen', 'Golf 8', 'BERLINE', 120.00, 'DISPONIBLE', 1),
(2, '225 TUN 2002', 'Renault', 'Clio 5', 'CITADINE', 75.00, 'DISPONIBLE', 1),
(3, '230 TUN 3003', 'Hyundai', 'Tucson', 'SUV', 160.00, 'LOUE', 2),
(4, '218 TUN 4004', 'Peugeot', 'Partner', 'UTILITAIRE', 90.00, 'DISPONIBLE', 2),
(5, '235 TUN 5005', 'Mercedes-Benz', 'Classe C', 'BERLINE', 250.00, 'DISPONIBLE', 3),
(6, '222 TUN 6006', 'Toyota', 'Yaris', 'CITADINE', 70.00, 'MAINTENANCE', 1);

-- 6. Véhicule Equipements
INSERT IGNORE INTO vehicule_equipement (id_vehicule, id_equipement) VALUES
(1, 1), (1, 3), (1, 4),
(2, 3),
(3, 1), (3, 2), (3, 3), (3, 4), (3, 6),
(4, 3),
(5, 1), (5, 3), (5, 4), (5, 5), (5, 6),
(6, 3);

-- 7. Réservations
INSERT IGNORE INTO reservation (id_reservation, date_debut, date_fin, statut, id_client, id_vehicule) VALUES
(1, '2026-11-01', '2026-11-05', 'CONFIRMEE', 1, 1),
(2, '2026-10-25', '2026-10-28', 'EN_ATTENTE', 2, 3),
(3, '2026-09-10', '2026-09-15', 'TERMINEE', 3, 2);

-- 8. Contrats
INSERT IGNORE INTO contrat (id_contrat, date_signature, montant_total, valide, id_reservation) VALUES
(1, '2026-10-28', 480.00, 1, 1),
(2, '2026-09-10', 375.00, 1, 3);

-- 9. Paiements
INSERT IGNORE INTO paiement (id_paiement, date_paiement, mode_paiement, montant, id_contrat) VALUES
(1, '2026-10-28', 'CARTE', 480.00, 1),
(2, '2026-09-10', 'ESPECES', 375.00, 2);

-- 10. Maintenances
INSERT IGNORE INTO maintenance (id_maintenance, date_debut, date_fin, description, id_vehicule) VALUES
(1, '2026-10-20', '2026-10-27', 'Vidange complète et changement plaquettes de frein', 6);
