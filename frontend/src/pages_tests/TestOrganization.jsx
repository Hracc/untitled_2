import { useState } from "react";
import { postStatement } from "../api/organization/organization";

export function TestOrganization() {
    const [form, setForm] = useState({
        organizationFullName: "",
        organizationShortName: "",
        inn: "",
        kpp: "",
        ogrn: "",
        responsiblePersonSurname: "",
        responsiblePersonName: "",
        responsiblePersonPatronymic: "",
        responsiblePersonEmail: "",
        responsiblePersonPhoneNumber: "",
        addInfo: ""
      });
      
      const fields = [
        { key: "organizationFullName", label: "Полное название" },
        { key: "organizationShortName", label: "Краткое название" },
        { key: "inn", label: "ИНН" },
        { key: "kpp", label: "КПП" },
        { key: "ogrn", label: "ОГРН" },
        { key: "responsiblePersonSurname", label: "Фамилия" },
        { key: "responsiblePersonName", label: "Имя" },
        { key: "responsiblePersonPatronymic", label: "Отчество" },
        { key: "responsiblePersonEmail", label: "Email" },
        { key: "responsiblePersonPhoneNumber", label: "Телефон" },
        { key: "addInfo", label: "Доп. информация", type: "textarea" }
      ];
      
      return (
        <>
          {fields.map((field) => (
            <div key={field.key}>
              <label>{field.label}</label>
                <br />
              {field.type === "textarea" ? (
                <textarea
                  value={form[field.key]}
                  onChange={(e) => setForm({ ...form, [field.key]: e.target.value })}
                />
              ) : (
                <input
                  type="text"
                  value={form[field.key]}
                  onChange={(e) => setForm({ ...form, [field.key]: e.target.value })}
                />
              )
              }
                <br />
                <br />
            </div>
          ))}
          <button
            onClick={async()=>{
                try {
                    await postStatement(form)}                    
                 catch (error) {
                }
            }}
          >Отправить</button>
        </>
      );
}