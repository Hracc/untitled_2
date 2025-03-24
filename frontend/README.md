# Предисловия:
В данной ветке на текущий момент идёт работа с объединением фронта и бэка, так что ниже описаны как их запускать.
# Инструкция для запуска проекта:
## backend:
Вариант через IDEA:
- запустить IDEA;
- при запуске проект IDEA принять предложения считать maven, если классы не засчитываются программой
- в IDEA запустить src/main/java/com/agregator/Agregator/AgregatorApplication
- если ошибки не произошли и вывелось в консоли "Tomcat started on port 8080 (http)", то сервер работает
## frontend:
Вариант без Visual Code:
- запустить cmd в папке frontend или ввести к ней путь
- ввести npm run dev
- консоль выведет ссылку на сайт и через ctrl перейти на сайт
Вариант с Visual Code:
- запустить Visual Code и выбрать папку frontend;
- сверху вкладка terminal нажать new terminal
- ввести npm run dev
- консоль выведет ссылку на сайт и через ctrl перейти на 
# API Fetch
Все скрипты fetch находятся в src/api
Ссылка на запросы: http://localhost:8080/swagger-ui/index.html#/ 
# React + Vite

This template provides a minimal setup to get React working in Vite with HMR and some ESLint rules.

Currently, two official plugins are available:

- [@vitejs/plugin-react](https://github.com/vitejs/vite-plugin-react/blob/main/packages/plugin-react/README.md) uses [Babel](https://babeljs.io/) for Fast Refresh
- [@vitejs/plugin-react-swc](https://github.com/vitejs/vite-plugin-react-swc) uses [SWC](https://swc.rs/) for Fast Refresh

## Expanding the ESLint configuration

If you are developing a production application, we recommend using TypeScript and enable type-aware lint rules. Check out the [TS template](https://github.com/vitejs/vite/tree/main/packages/create-vite/template-react-ts) to integrate TypeScript and [`typescript-eslint`](https://typescript-eslint.io) in your project.


