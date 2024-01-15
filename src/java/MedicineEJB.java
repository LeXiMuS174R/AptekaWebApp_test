
import jakarta.annotation.PreDestroy;
import java.util.List;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

@Stateless
public class MedicineEJB {

    @PersistenceContext(unitName = "apteka_medicine")
    private EntityManager em;

    public List<Medicine> getAllMedicines() {
        return em.createQuery("SELECT m FROM Medicine m", Medicine.class).getResultList();
    }

    public Medicine getMedicineById(int id) {
        return em.find(Medicine.class, id);
    }

    @PreDestroy
    public void destruct() {
        em.close();
    }
}
