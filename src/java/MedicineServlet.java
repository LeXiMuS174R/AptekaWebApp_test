// MedicineServlet.java

//import jakarta.activation.DataSource;
import jakarta.annotation.Resource;
import java.io.IOException;
import jakarta.ejb.EJB;
import java.sql.Connection;
import javax.sql.DataSource;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/MedicineServlet")
public class MedicineServlet extends HttpServlet {

    @Resource(name = "jdbc/aptekaDB")    //java:comp/env/jdbc/aptekaDB
    private DataSource dataSource;

    @EJB
    private MedicineDAO medicineDAO;

    @Override
    public void init() throws ServletException {
        try {
            super.init();
            Connection connection = MedicineDAO.getConnection();
            medicineDAO = new MedicineDAO(connection);
        } catch (SQLException ex) {
            Logger.getLogger(MedicineServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            // Логика для редактирования
            int id = Integer.parseInt(request.getParameter("id"));
            Medicine medicine = medicineDAO.getMedicineById(id);

            // Убедимся, что medicine не равен null, прежде чем установить атрибут
            if (medicine != null) {
                request.setAttribute("medicine", medicine);
            }
        }

        // Логика для отображения списка лекарств
        List<Medicine> medicines = medicineDAO.getAllMedicines();
        request.setAttribute("medicines", medicines);

        // Перенаправление на JSP страницу
        request.getRequestDispatcher("/viewMedicines.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equals(action)) {
            // Логика для добавления лекарства
            Medicine medicine = new Medicine();
            medicine.setName(request.getParameter("name"));
            medicine.setQuantity(Integer.parseInt(request.getParameter("quantity")));

            medicineDAO.addMedicine(medicine);
        } else if ("edit".equals(action)) {
            // Логика для редактирования лекарства
            Medicine medicine = new Medicine();
            medicine.setId(Integer.parseInt(request.getParameter("id")));
            medicine.setName(request.getParameter("name"));
            medicine.setQuantity(Integer.parseInt(request.getParameter("quantity")));

            medicineDAO.updateMedicine(medicine);
        }

        // После выполнения действия, перенаправьте пользователя на doGet для обновления данных на странице
        doGet(request, response);
    }
}
