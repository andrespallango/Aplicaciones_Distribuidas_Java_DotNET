using DataAccess.Entities;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace DataAccess.Contracts
{
    public interface IEmployeeRepository: IGenericRepository<Employee>
    {
        //otros metodos 
        //IEnumerable<Employee> GetBySalary();
    }
}
