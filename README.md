# Issue Tracker
This repository contains the issue tracker project developed as a collaborative project exercise.
## Project Overview

# :point_right: Kodstar Issue Tracker Project Group 1

>This repository is created for the final project of Kodstar and build by Spring Boot and ReactJS.
One of the main outcomes of this project is figuring out how to create, explore and run a web application in a development environment, then deploying it to the production environment in accordance with **Test-Driven-Development(TDD)** approach. 

> **Issue Tracker** is an application where a logged-in user manages this process by creating a project and categorizing it in chunks.

>Our main goal was not to deliver fully-featured web application, but instead to create a minimum viable product in 4 weeks. We had to work in an environment where Covid-19 measures were in place. That means, we had to work remotely, but that shouldn't hamper our development process. In order to overcome the side-effects of remote working, we set-up daily working sessions between team members and used the benefit of collaborative coding.  

> At the very beginning, our biggest challenge was setting-up the environment for **Continuous Integration and Continuous Deployment (CI/CD)**. Once this was in place, we managed to progress smoothly. Of course, as every developer we stucked a lot ;) We spent hours in exploring documentations, StackOverFlow and so on which only improved our soft skills!  

>You may find the details of our project in the following lines. We are aware of the fact that there is so much room to be improved in this project, like establishing connections between the matched users (similar in Instagram, Facebook), uploading pictures of the users, setting-up email authentication and so on. You are more than welcomed to contribute in this project by opening an issue or sending a pull request. **We are stronger together!** 

---
## Index
* [Learning Objectives and Supported Skills](#learning-objectives-and-supported-skills)
* [Setup Environment](#setup-environment)
	* [Installation](#installation)
	* [Running Application](#running-application)
  	* [Usage](#Usage)
* [Technology-Tool-Stack](#technology-tool-stack)
* [Contributing](#contributing)
* [License](#credits)

---

## Learning Objectives and Supported Skills
* Creating a user friendly React Application (using Functional Bases Components and Hooks)
* Building wireframes, mocks and prototype of pages
* Setting up a professional folder structure
* Understanding Test-Driven-Development(TDD)
* Implementing Continuous Integration and Continuous Deployment (CI/CD)
* Using GitHub Project Board as project management tool
* Debugging React code in IDE
* Exploring and understanding DOM
* Understanding code which is written by your team mate

---
## Setup Environment

### installation:
- Clone Develoment main branch of this repo
- Install all Frontend dependencies in **/issue-tracker-2020-1/web** folder --> run **npm install** in the terminal

### running-application
  In the root folder **/issue-tracker-2020-1**,
  - To run only frontend -->run **cd web && npm start** in the terminal
  
### usage

* The login page will greet you when you first log into the application. If you have not registered before, you can register by clicking on the register page and filling out the form.
* After you have registered, you will be directed to the project page by the login. On this page, you can create a new project or view the progress of existing projects.
* You can create a new issue in any project, edit or delete this issue.You can create a new issue in any project, edit or delete this issue.
* You can add a label to issues from the existing label list or create by yourself.
* You can move issues to different categories with drag and drop.
* It can be "closed" after the problem is resolved or it is found unnecessary.


## Technology-Tool-Stack

- **React** : React. js is an open-source JavaScript library that is used for building user interfaces specifically for single-page applications. It's used for handling the view layer for web and mobile apps. React also allows us to create reusable UI components.
- **Axios** : Axios is a Javascript library used to make HTTP requests from node.js or XMLHttpRequests from the browser that also supports the ES6 Promise API. 
- **Multiselect-react-dropdown** : A React component which provides multi select functionality with various features like selection limit, CSS customization, checkbox, search option, disable preselected values, flat array, keyboard navigation for accessibility and grouping features. Also it has feature to behave like normal dropdown(means single select dropdown).
- **React-beautiful-dnd** : It does an incredible job at providing a great set of drag and drop primitives which work especially well with the wildly inconsistent html5 drag and drop feature. react-beautiful-dnd is a higher level abstraction specifically built for lists (vertical, horizontal, movement between lists, nested lists and so on).
- **React-color** :  A collection of color picker components for React that mimics the color picker from popular applications like Sketch, Photoshop, Chrome, and many others.
- **React-loader-spinner** : react-spinner-loader provides simple React SVG spinner component which can be implemented for async await operation before data loads to the view.


---

## Contributing
   - You are more than welcomed to contribute in this project by opening an issue or sending a pull request.
---

## License
   - Kodstar









Target of the project is to develop a web-based issue tracker.
## Project Team
[@KodstarBootcamp/issue-tracker-2020-1](https://github.com/orgs/KodstarBootcamp/teams/issue-tracker-2020-1/members)
## Project Way of Working
The way of working followed by the team is documented [here](https://github.com/KodstarBootcamp/curriculum/blob/master/project/way-of-working.md)
## Project Structure
Project is decomposed into layers and this is reflected to the directory structure.
* backend: backend of the application
* web: web based frontend of the application
* mobile: mobile fontend of the application
* test: end-to-end tests for the application

RESTful API between backend and web/mobile is documented [here](https://app.swaggerhub.com/apis-docs/Kodstar/Issue_Tracker/1.0.0)
