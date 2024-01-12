// MedicineServlet.java

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/MedicineServlet")
public class MedicineServlet extends HttpServlet {

    private MedicineDAO medicineDAO;

//    @Override
//    public void init() throws ServletException {
//        super.init();
//        // Инициализация объекта MedicineDAO при старте сервлета
//        // (вы можете использовать Dependency Injection для более сложных приложений)
//        Connection dbConnection = (Connection) getServletContext().getAttribute("dbConnection");
//        medicineDAO = new MedicineDAO(dbConnection);
//        medicineDAO.setConnection(dbConnection);
//    }

    @Override
    public void init() throws ServletException {
        super.init();
        // Инициализация объекта MedicineDAO при старте сервлета
        // (вы можете использовать Dependency Injection для более сложных приложений)
        Connection dbConnection = (Connection) getServletContext().getAttribute("dbConnection");
        medicineDAO = new MedicineDAO(dbConnection);
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
