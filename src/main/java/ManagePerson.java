import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


/**
 * Created by DOTIN SCHOOL 3 on 6/2/2015.
 */
public class ManagePerson {
    static SessionFactory sessionFactory;
    public static void main(String[] args){
        Configuration cfg = new Configuration().addResource("hibernate.cfg.xml").configure();
        StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
        try {
            sessionFactory = cfg.buildSessionFactory(ssrb.build());
        }catch (Throwable e){
            System.err.println("Failed to create session factory");
            throw new ExceptionInInitializerError(e);
        }
        ManagePerson managePerson=new ManagePerson();
        managePerson.addPerson();
    }

    public Integer addPerson(){
        Session session=sessionFactory.openSession();
        Transaction tx=null;
        Integer personId=null;
        try{
            tx=session.beginTransaction();
            Person p=new Person("BBBB");
            personId=(Integer) session.save(p);
            tx.commit();
        }catch (HibernateException e){
            if (tx!=null){
                tx.rollback();
                e.printStackTrace();
            }
        }finally {
            session.close();
        }
        return personId;
    }
}
