using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.Contracts;
using DataAccess.Entities;
using DataAccess.Repositories;
using Domain.ValueObjects;
using System.ComponentModel.DataAnnotations;

namespace Domain.Models
{
    public class EmployeeModel : IDisposable
    {
        private int idPk;
        private string idNumber;
        private string name;
        private string mail;
        private DateTime birthday;
        private int age;

        private IEmployeeRepository employeeRepository;
        public EntityState State
        {
            private get;
            set;
        }
        private List<EmployeeModel> listEmployees;

        // PROPIEDADES/MODELOS DE VISTA/ VALIDAR DATOS
        public int IdPk { get => idPk; set => idPk = value; }
        [Required(ErrorMessage = "El campo numero de identificación es requerida")]
        [RegularExpression("([0-9]+)", ErrorMessage = "El numero de identificacion solo debe ser numeros")]
        [StringLength(maximumLength: 10, MinimumLength = 10, ErrorMessage = "El numero de identificacion debe ser de 10 digitos")]
        public string IdNumber { get => idNumber; set => idNumber = value; }
        [Required]
        [RegularExpression("^[a-zA-Z ]+$", ErrorMessage = "El campo de nombre solo debe ser ingresado con letras")]
        [StringLength(maximumLength: 100, MinimumLength = 3)]
        public string Name { get => name; set => name = value; }
        [Required]
        [EmailAddress]
        public string Mail { get => mail; set => mail = value; }
        public DateTime Birthday { get => birthday; set => birthday = value; }
        public int Age { get => age; private set => age = value; }

        public EmployeeModel()
        {
            employeeRepository = new EmployeeRepository();
        }
        public string SaveChanges()
        {
            string message = null;
            try
            {
                var employeeDataModel = new Employee();
                employeeDataModel.idPk = idPk;
                employeeDataModel.name = name;
                employeeDataModel.mail = mail;
                employeeDataModel.birthday = birthday;

                switch (State)
                {
                    case EntityState.Added:
                        employeeRepository.Add(employeeDataModel);
                        message = "Registrado correctamente";
                        break;
                    case EntityState.Modified:
                        employeeRepository.Adit(employeeDataModel);
                        message = "Editado correctamente";
                        break;
                    case EntityState.Delete:
                        employeeRepository.Remove(idPk);
                        message = "Eliminado correctamente";
                        break;
                }
            }
            catch (Exception ex)
            {
                System.Data.SqlClient.SqlException sqlEx = ex as System.Data.SqlClient.SqlException;
                if (sqlEx != null && sqlEx.Number == 2627)
                    message = "Duplicate record";
                else
                    message = ex.ToString();
            }
            return message;
        }
        public List<EmployeeModel> GetAll()
        {
            var employeeDataModel = employeeRepository.GetAll();
            listEmployees = new List<EmployeeModel>();
            foreach (Employee item in employeeDataModel)
            {
                listEmployees.Add(new EmployeeModel
                {
                    idPk = item.idPk,
                    idNumber = item.idNumber,
                    name = item.name,
                    mail = item.mail,
                    birthday = item.birthday,
                    age = CalculateAge(birthday)

                });
            }
            return listEmployees;
        }

        public IEnumerable<EmployeeModel> FindById(string filter)
        {
            return listEmployees.FindAll(e => e.IdNumber.Contains(filter) || e.Name.Contains(filter));
        }
        private int CalculateAge(DateTime dateOfBirth)
        {
            DateTime currentDate = DateTime.Now;

            int age = currentDate.Year - dateOfBirth.Year;

            // Restar un año si el mes y el día de la fecha de nacimiento aún no han pasado este año
            if (currentDate.Month < dateOfBirth.Month || (currentDate.Month == dateOfBirth.Month && currentDate.Day < dateOfBirth.Day))
            {
                age--;
            }

            return age;

        }

        public void Dispose()
        {
            
        }
    }
}
