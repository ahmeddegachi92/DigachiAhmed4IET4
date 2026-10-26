package tn.espritDigachiAhmed4iet4.Domain;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.spi.MetadataImplementor;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DomainAssociationsTests {

    @Test
    void associationMappingsAreValidWithoutDatabaseConnection() {
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .applySetting("hibernate.dialect", "org.hibernate.dialect.MySQLDialect")
                .applySetting("hibernate.boot.allow_jdbc_metadata_access", false)
                .build();
        try {
            MetadataImplementor metadata = (MetadataImplementor) new MetadataSources(registry)
                    .addAnnotatedClass(Agence.class)
                    .addAnnotatedClass(Employe.class)
                    .addAnnotatedClass(Vehicule.class)
                    .addAnnotatedClass(Maintenance.class)
                    .addAnnotatedClass(Equipement.class)
                    .addAnnotatedClass(Client.class)
                    .addAnnotatedClass(Reservation.class)
                    .addAnnotatedClass(Contrat.class)
                    .addAnnotatedClass(Paiement.class)
                    .buildMetadata();

            assertEquals(9, metadata.getEntityBindings().size());
            assertEquals(8, metadata.getCollectionBindings().size());
            assertDoesNotThrow(metadata::validate);
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Test
    void bidirectionalAssociationsDoNotCauseRecursiveToString() {
        Agence agence = new Agence();
        Employe employe = new Employe();
        Vehicule vehicule = new Vehicule();
        Maintenance maintenance = new Maintenance();
        Equipement equipement = new Equipement();
        Client client = new Client();
        Reservation reservation = new Reservation();
        Contrat contrat = new Contrat();
        Paiement paiement = new Paiement();

        agence.setEmployes(List.of(employe));
        employe.setAgence(agence);
        agence.setVehicules(List.of(vehicule));
        vehicule.setAgence(agence);
        vehicule.setMaintenances(List.of(maintenance));
        maintenance.setVehicule(vehicule);
        vehicule.setEquipements(Set.of(equipement));
        equipement.setVehicules(Set.of(vehicule));
        vehicule.setReservations(List.of(reservation));
        reservation.setVehicule(vehicule);
        client.setReservations(List.of(reservation));
        reservation.setClient(client);
        reservation.setContrat(contrat);
        contrat.setReservation(reservation);
        contrat.setPaiements(List.of(paiement));
        paiement.setContrat(contrat);

        for (Object entity : List.of(agence, employe, vehicule, maintenance, equipement,
                client, reservation, contrat, paiement)) {
            assertDoesNotThrow(entity::toString);
        }
    }

    @Test
    void associationOwnershipFetchAndCascadesMatchSpecification() throws NoSuchFieldException {
        assertOneToMany(Agence.class, "vehicules", "agence");
        assertOneToMany(Agence.class, "employes", "agence");
        assertManyToOne(Vehicule.class, "agence", "id_agence");
        assertManyToOne(Employe.class, "agence", "id_agence");
        assertOneToMany(Client.class, "reservations", "client", CascadeType.PERSIST);
        assertManyToOne(Reservation.class, "client", "id_client");
        assertOneToMany(Vehicule.class, "reservations", "vehicule");
        assertManyToOne(Reservation.class, "vehicule", "id_vehicule");
        assertOneToMany(Vehicule.class, "maintenances", "vehicule", CascadeType.PERSIST);
        assertManyToOne(Maintenance.class, "vehicule", "id_vehicule");

        var contratPaiements = Contrat.class.getDeclaredField("paiements")
                .getAnnotation(OneToMany.class);
        assertEquals("contrat", contratPaiements.mappedBy());
        assertEquals(FetchType.LAZY, contratPaiements.fetch());
        assertArrayEquals(new CascadeType[] {CascadeType.ALL}, contratPaiements.cascade());
        assertTrue(contratPaiements.orphanRemoval());
        assertManyToOne(Paiement.class, "contrat", "id_contrat");

        var reservationContrat = Reservation.class.getDeclaredField("contrat")
                .getAnnotation(OneToOne.class);
        assertEquals("reservation", reservationContrat.mappedBy());
        assertEquals(FetchType.LAZY, reservationContrat.fetch());
        assertArrayEquals(new CascadeType[] {CascadeType.ALL}, reservationContrat.cascade());

        var contratReservation = Contrat.class.getDeclaredField("reservation");
        var oneToOne = contratReservation.getAnnotation(OneToOne.class);
        assertEquals("", oneToOne.mappedBy());
        assertEquals(FetchType.LAZY, oneToOne.fetch());
        assertArrayEquals(new CascadeType[0], oneToOne.cascade());
        var joinColumn = contratReservation.getAnnotation(JoinColumn.class);
        assertEquals("id_reservation", joinColumn.name());
        assertTrue(joinColumn.unique());

        var equipements = Vehicule.class.getDeclaredField("equipements");
        assertEquals(Set.class, equipements.getType());
        var manyToMany = equipements.getAnnotation(ManyToMany.class);
        assertEquals("", manyToMany.mappedBy());
        assertEquals(FetchType.LAZY, manyToMany.fetch());
        assertArrayEquals(new CascadeType[0], manyToMany.cascade());
        var joinTable = equipements.getAnnotation(JoinTable.class);
        assertEquals("vehicule_equipement", joinTable.name());
        assertEquals(1, joinTable.joinColumns().length);
        assertEquals("id_vehicule", joinTable.joinColumns()[0].name());
        assertEquals(1, joinTable.inverseJoinColumns().length);
        assertEquals("id_equipement", joinTable.inverseJoinColumns()[0].name());

        var vehicules = Equipement.class.getDeclaredField("vehicules");
        assertEquals(Set.class, vehicules.getType());
        var inverseManyToMany = vehicules.getAnnotation(ManyToMany.class);
        assertEquals("equipements", inverseManyToMany.mappedBy());
        assertEquals(FetchType.LAZY, inverseManyToMany.fetch());
        assertArrayEquals(new CascadeType[0], inverseManyToMany.cascade());
    }

    private static void assertOneToMany(Class<?> entity, String field, String mappedBy,
            CascadeType... cascades) throws NoSuchFieldException {
        var association = entity.getDeclaredField(field).getAnnotation(OneToMany.class);
        assertEquals(mappedBy, association.mappedBy());
        assertEquals(FetchType.LAZY, association.fetch());
        assertArrayEquals(cascades, association.cascade());
        assertEquals(false, association.orphanRemoval());
    }

    private static void assertManyToOne(Class<?> entity, String field, String column)
            throws NoSuchFieldException {
        var associationField = entity.getDeclaredField(field);
        var association = associationField.getAnnotation(ManyToOne.class);
        assertEquals(FetchType.LAZY, association.fetch());
        assertArrayEquals(new CascadeType[0], association.cascade());
        assertEquals(column, associationField.getAnnotation(JoinColumn.class).name());
    }
}
