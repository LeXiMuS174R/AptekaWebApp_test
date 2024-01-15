// MedicineServlet.java

import jakarta.activation.DataSource;
import jakarta.annotation.Resource;
import java.io.IOException;
import jakarta.ejb.EJB;
import jakarta.jms.Connection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/MedicineServlet")
public class MedicineServlet extends HttpServlet {

    @EJB
    private MedicineEJB medicineEJB;

    @Resource(name = "aptekaDB")    //java:comp/env/jdbc/aptekaDB
    private DataSource dataSource;

    private MedicineDAO medicineDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        Connection connection = MedicineDAO.getConnection();
        medicineDAO = new MedicineDAO(connection);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("edit".equals(action)) {
            // Логика для редактирования
            int id = Integer.parseInt(request.getParameter("id"));
            Medicine medicine = medicineEJB.getMedicineById(id);

            // Убедимся, что medicine не равен null, прежде чем установить атрибут
            if (medicine != null) {
                request.setAttribute("medicine", medicine);
            }
        }

        // Логика для отображения списка лекарств
        List<Medicine> medicines = medicineEJB.getAllMedicines();
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
