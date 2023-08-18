package TaskManagementSystem.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import TaskManagementSystem.entity.Employee;
import TaskManagementSystem.entity.Login;
import TaskManagementSystem.enums.Roles;
import TaskManagementSystem.repository.EmployeeRepository;
import TaskManagementSystem.repository.LoginRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
	@Autowired
	EmployeeRepository empRepo;
	@Autowired
	LoginRepository logRepo;
	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public List<Employee> findAll() {
		return empRepo.findAll();
	}

	@Override
	public Employee findById(int theId) {
		Optional<Employee> opt = empRepo.findById(theId);
		if (opt.isPresent()) {
			return opt.get();
		}
		return null;
	}

	@Override
	public Employee save(Employee theEmp) {
		if (theEmp.getEmpRole() == Roles.ADMIN) {
			theEmp.setEmpId(-1);
			theEmp.setManagerId(-1);
		}
		Employee emp = empRepo.findFirstByOrderByEmpIdDesc();
		if (theEmp.getEmpId() == 0) {
			if (emp != null)
				theEmp.setEmpId(emp.getEmpId() + 1);
			else
				theEmp.setEmpId(100);
			Login logCheck = logRepo.findByUsername(theEmp.getEmpEmail());
			if (logCheck != null)
				return null;
			Login log = new Login(theEmp.getEmpEmail(), theEmp.getEmpPassword(), theEmp.getEmpRole());
			logRepo.save(log);
		} else {
			Query query = new Query(Criteria.where("username").is(theEmp.getEmpEmail()));
			Update update = new Update().set("password", theEmp.getEmpPassword());

			mongoTemplate.updateFirst(query, update, Login.class);
		}
		return empRepo.save(theEmp);
	}

	@Override
	public void deleteById(int theId) {
		Optional<Employee> opt = empRepo.findById(theId);
		if (opt.isPresent()) {
			Employee emp = opt.get();
			logRepo.deleteByKey(emp.getEmpEmail());
			empRepo.deleteById(theId);
		}
	}

	@Override
	public long getCount() {
		return empRepo.count();
	}

	@Override
	public List<Employee> findByManagerId(int theId) {
		return empRepo.findByManagerId(theId);
	}

	@Override
	public Employee findByEmail(String email) {
		return empRepo.findByEmpEmail(email);
	}

}