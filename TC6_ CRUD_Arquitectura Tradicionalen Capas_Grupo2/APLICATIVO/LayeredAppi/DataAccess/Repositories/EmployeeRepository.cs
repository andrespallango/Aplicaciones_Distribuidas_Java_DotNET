﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using DataAccess.Contracts;
using DataAccess.Entities;
using System.Data;
using System.Data.SqlClient;

namespace DataAccess.Repositories
{
    public class EmployeeRepository : MasterRepository, IEmployeeRepository
    {
        //Campos
        private string selectAll;
        private string insert;
        private string update;
        private string delete;
        //Propiedades
        //::
        //Constructores
        public EmployeeRepository() {
            selectAll = "select *from Employee";
            insert = "insert into Employee values (@idNumber,@name,@mail,@birthday)";
            update = "update Employee set IdNumber=@idNumber,Name=@name,Mail=@mail,Birthday=@birthday where idPk=@idPK";
            delete = "delete from Employee where idPk=@idPK";
        }
        //Metodos, Comportamientos 
        public int Add(Employee entity)
        {
            parameters = new List<SqlParameter>();
            parameters.Add(new SqlParameter("@idNumber", entity.idNumber));
            parameters.Add(new SqlParameter("@name", entity.name));
            parameters.Add(new SqlParameter("@mail", entity.mail));
            parameters.Add(new SqlParameter("@birthday", entity.birthday));
            return ExecuteNonQuery(insert);
        }

        public int Adit(Employee entity)
        {
            parameters = new List<SqlParameter>();
            parameters.Add(new SqlParameter("@idPK", entity.idPk));
            parameters.Add(new SqlParameter("@idNumber", entity.idNumber));
            parameters.Add(new SqlParameter("@name", entity.name));
            parameters.Add(new SqlParameter("@mail", entity.mail));
            parameters.Add(new SqlParameter("@birthday", entity.birthday));
            return ExecuteNonQuery(update);
        }

        public IEnumerable<Employee> GetAll()
        {
            var tableResult = ExecuteReader(selectAll);
            var listEmployees = new List<Employee>();
            foreach(DataRow item in tableResult.Rows)
            {
                listEmployees.Add(new Employee
                {
                    idPk = Convert.ToInt32(item[0]),
                    idNumber = item[1].ToString(),
                    name = item[2].ToString(),
                    mail = item[3].ToString(),
                    birthday = Convert.ToDateTime(item[4]),

                });
            }
            return listEmployees;
        }

        public int Remove(int idPk)
        {
            parameters = new List<SqlParameter>();
            parameters.Add(new SqlParameter("@idPK", idPk));
            return ExecuteNonQuery(delete);
        }

    }
}
