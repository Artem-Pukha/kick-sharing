package com.spnsolo.repository;

import com.spnsolo.entity.Coordinates;
import com.spnsolo.entity.Scooter;
import com.spnsolo.model.SaveScooter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class ScooterRepository {

    private final SessionFactory sessionFactory;

    public ScooterRepository(SessionFactory sessionFactory){ this.sessionFactory = sessionFactory; }

    public Scooter create(Scooter request) {
        Session session = sessionFactory.getCurrentSession();
        session.save(request);
        return request;
    }

    public Optional<Scooter> getById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Scooter.class,id));
    }

    public Scooter update(Scooter request) {
        Session session = sessionFactory.getCurrentSession();
        session.save(request);
        return request;
    }

    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Scooter scooter = session.get(Scooter.class,id);
        session.remove(scooter);
    }

    public List<Scooter> getAllAvailable(){
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Scooter> allAvailable = session.createQuery("""
                from Scooter where available = 1
                """,Scooter.class);
        return allAvailable.getResultList();
    }

}
