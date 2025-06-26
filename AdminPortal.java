import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AdminPortal extends JFrame {
    private List<Classroom> classrooms;
    private List<Teacher> teachers;
    private List<Subject> subjects;
    
    private JTabbedPane tabbedPane;
    private JTable classroomTable;
    private JTable teacherTable;
    private JTable subjectTable;
    
    private DefaultTableModel classroomModel;
    private DefaultTableModel teacherModel;
    private DefaultTableModel subjectModel;
    
    private String adminUsername;
    private DataManager dataManager;

    public AdminPortal() {
        dataManager = DataManager.getInstance();
        this.adminUsername = "Admin"; // Default username
        initializeData();
        setupGUI();
    }
    
    public AdminPortal(String username) {
        dataManager = DataManager.getInstance();
        this.adminUsername = username;
        initializeData();
        setupGUI();
    }

    private void initializeData() {
        // Initialize lists from DataManager
        classrooms = dataManager.getClassrooms();
        teachers = dataManager.getTeachers();
        subjects = dataManager.getSubjects();

        // Clear existing data to ensure clean initialization
        if (subjects.isEmpty()) {
            dataManager.clearAllData();  // Add this method to DataManager
            // Add classrooms with different capacities and facilities
            // Computer Science Labs
            Classroom csLab1 = new Classroom("CS Lab 1", 30, true);
            Classroom csLab2 = new Classroom("CS Lab 2", 30, true);
            Classroom csLab3 = new Classroom("CS Lab 3", 25, true);
            
            // Engineering Labs
            Classroom engLab1 = new Classroom("Engineering Lab 1", 25, true);
            Classroom engLab2 = new Classroom("Engineering Lab 2", 25, true);
            Classroom engLab3 = new Classroom("Engineering Lab 3", 25, true);
            
            // Science Labs
            Classroom sciLab1 = new Classroom("Science Lab 1", 20, true);
            Classroom sciLab2 = new Classroom("Science Lab 2", 20, true);
            Classroom sciLab3 = new Classroom("Science Lab 3", 20, true);
            
            // Regular Classrooms
            Classroom class101 = new Classroom("Class 101", 40, true);
            Classroom class102 = new Classroom("Class 102", 40, true);
            Classroom class103 = new Classroom("Class 103", 40, false);
            Classroom class104 = new Classroom("Class 104", 40, false);
            Classroom class201 = new Classroom("Class 201", 35, true);
            Classroom class202 = new Classroom("Class 202", 35, true);
            Classroom class203 = new Classroom("Class 203", 35, false);
            Classroom class204 = new Classroom("Class 204", 35, false);
            
            // Conference Rooms
            Classroom conf1 = new Classroom("Conference Room 1", 20, true);
            Classroom conf2 = new Classroom("Conference Room 2", 15, true);
            
            // Add all classrooms to data manager and list
            Classroom[] allClassrooms = {
                csLab1, csLab2, csLab3,
                engLab1, engLab2, engLab3,
                sciLab1, sciLab2, sciLab3,
                class101, class102, class103, class104,
                class201, class202, class203, class204,
                conf1, conf2
            };
            
            for (Classroom classroom : allClassrooms) {
                dataManager.addClassroom(classroom);
                classrooms.add(classroom);
            }
        }

        if (teachers.isEmpty()) {
            // Computer Science Department
            Teacher teacher1 = new Teacher("Dr. Sarah Johnson", "Computer Science", "sarah.j@university.edu", "123-456-7890");
            teacher1.addSubject("Java Programming");
            teacher1.addSubject("Data Structures");
            teacher1.addSubject("Algorithms");
            teacher1.addSubject("Database Systems");
            dataManager.addTeacher(teacher1);
            teachers.add(teacher1);

            // Add Dr. Alex Kumar
            Teacher teacher2 = new Teacher("Dr. Alex Kumar", "Computer Science", "alex.k@university.edu", "123-456-7891");
            teacher2.addSubject("Operating Systems");
            teacher2.addSubject("Computer Networks");
            teacher2.addSubject("Software Engineering");
            teacher2.addSubject("Web Development");
            teacher2.addSubject("Mobile App Development");
            teacher2.addSubject("Artificial Intelligence");
            dataManager.addTeacher(teacher2);
            teachers.add(teacher2);

            // Add Prof. Michael Chen
            Teacher teacher3 = new Teacher("Prof. Michael Chen", "Mathematics", "michael.c@university.edu", "123-456-7892");
            teacher3.addSubject("Calculus I");
            teacher3.addSubject("Linear Algebra");
            teacher3.addSubject("Number Theory");
            teacher3.addSubject("Differential Equations");
            teacher3.addSubject("Complex Analysis");
            teacher3.addSubject("Graph Theory");
            dataManager.addTeacher(teacher3);
            teachers.add(teacher3);

            // Add Prof. Rachel Green
            Teacher teacher4 = new Teacher("Prof. Rachel Green", "Mathematics", "rachel.g@university.edu", "123-456-7893");
            teacher4.addSubject("Probability Theory");
            teacher4.addSubject("Statistics");
            dataManager.addTeacher(teacher4);
            teachers.add(teacher4);

            // Add Dr. Emily Rodriguez
            Teacher teacher5 = new Teacher("Dr. Emily Rodriguez", "Physics", "emily.r@university.edu", "123-456-7894");
            teacher5.addSubject("Classical Mechanics");
            teacher5.addSubject("Quantum Physics");
            teacher5.addSubject("Thermodynamics");
            teacher5.addSubject("Electromagnetic Theory");
            teacher5.addSubject("Nuclear Physics");
            teacher5.addSubject("Astrophysics");
            dataManager.addTeacher(teacher5);
            teachers.add(teacher5);

            // Add Dr. Carlos Martinez
            Teacher teacher6 = new Teacher("Dr. Carlos Martinez", "Physics", "carlos.m@university.edu", "123-456-7895");
            teacher6.addSubject("Optics");
            dataManager.addTeacher(teacher6);
            teachers.add(teacher6);

            // Add Prof. James Wilson
            Teacher teacher7 = new Teacher("Prof. James Wilson", "Chemistry", "james.w@university.edu", "123-456-7896");
            teacher7.addSubject("Organic Chemistry");
            teacher7.addSubject("Physical Chemistry");
            teacher7.addSubject("Analytical Chemistry");
            teacher7.addSubject("Inorganic Chemistry");
            teacher7.addSubject("Polymer Chemistry");
            teacher7.addSubject("Nuclear Chemistry");
            dataManager.addTeacher(teacher7);
            teachers.add(teacher7);

            // Add Prof. Anna Smith
            Teacher teacher8 = new Teacher("Prof. Anna Smith", "Chemistry", "anna.s@university.edu", "123-456-7897");
            teacher8.addSubject("Biochemistry");
            dataManager.addTeacher(teacher8);
            teachers.add(teacher8);

            // Add Dr. Lisa Kim
            Teacher teacher9 = new Teacher("Dr. Lisa Kim", "Biology", "lisa.k@university.edu", "123-456-7898");
            teacher9.addSubject("Cell Biology");
            teacher9.addSubject("Genetics");
            teacher9.addSubject("Microbiology");
            teacher9.addSubject("Ecology");
            teacher9.addSubject("Marine Biology");
            teacher9.addSubject("Immunology");
            dataManager.addTeacher(teacher9);
            teachers.add(teacher9);

            // Add Dr. John Davis
            Teacher teacher10 = new Teacher("Dr. John Davis", "Biology", "john.d@university.edu", "123-456-7899");
            teacher10.addSubject("Evolutionary Biology");
            dataManager.addTeacher(teacher10);
            teachers.add(teacher10);

            // Add Prof. David Thompson
            Teacher teacher11 = new Teacher("Prof. David Thompson", "Electrical Engineering", "david.t@university.edu", "123-456-7900");
            teacher11.addSubject("Circuit Analysis");
            teacher11.addSubject("Digital Electronics");
            teacher11.addSubject("Control Systems");
            teacher11.addSubject("Power Systems");
            teacher11.addSubject("Microelectronics");
            teacher11.addSubject("Robotics");
            dataManager.addTeacher(teacher11);
            teachers.add(teacher11);

            // Add Dr. Maria Garcia
            Teacher teacher12 = new Teacher("Dr. Maria Garcia", "Mechanical Engineering", "maria.g@university.edu", "123-456-7901");
            teacher12.addSubject("Statics");
            teacher12.addSubject("Dynamics");
            teacher12.addSubject("Fluid Mechanics");
            teacher12.addSubject("Machine Design");
            teacher12.addSubject("Thermodynamics II");
            teacher12.addSubject("Robotics and Automation");
            dataManager.addTeacher(teacher12);
            teachers.add(teacher12);

            // Add Prof. Robert Taylor
            Teacher teacher13 = new Teacher("Prof. Robert Taylor", "Civil Engineering", "robert.t@university.edu", "123-456-7902");
            teacher13.addSubject("Structural Analysis");
            teacher13.addSubject("Geotechnical Engineering");
            teacher13.addSubject("Transportation Engineering");
            teacher13.addSubject("Environmental Engineering");
            teacher13.addSubject("Construction Management");
            teacher13.addSubject("Earthquake Engineering");
            dataManager.addTeacher(teacher13);
            teachers.add(teacher13);

            // Add Dr. Jennifer Lee
            Teacher teacher14 = new Teacher("Dr. Jennifer Lee", "Psychology", "jennifer.l@university.edu", "123-456-7903");
            teacher14.addSubject("Cognitive Psychology");
            teacher14.addSubject("Developmental Psychology");
            teacher14.addSubject("Clinical Psychology");
            teacher14.addSubject("Social Psychology");
            teacher14.addSubject("Behavioral Psychology");
            teacher14.addSubject("Educational Psychology");
            dataManager.addTeacher(teacher14);
            teachers.add(teacher14);

            // Add Prof. William Brown
            Teacher teacher15 = new Teacher("Prof. William Brown", "Economics", "william.b@university.edu", "123-456-7904");
            teacher15.addSubject("Microeconomics");
            teacher15.addSubject("Macroeconomics");
            teacher15.addSubject("Econometrics");
            teacher15.addSubject("International Trade");
            teacher15.addSubject("Development Economics");
            teacher15.addSubject("Financial Economics");
            dataManager.addTeacher(teacher15);
            teachers.add(teacher15);

            // Create and add subjects with proper lecturer assignment
            Subject javaProg = new Subject("Java Programming", "CS101", 3);
            javaProg.addLecturer("Dr. Sarah Johnson");
            javaProg.setClassroom("CS Lab 1");
            javaProg.setTiming("Monday 8:00 AM - 9:00 AM");
            dataManager.addSubject(javaProg);
            subjects.add(javaProg);

            Subject dataStruct = new Subject("Data Structures", "CS102", 3);
            dataStruct.addLecturer("Dr. Sarah Johnson");
            dataStruct.setClassroom("CS Lab 2");
            dataStruct.setTiming("Tuesday 9:00 AM - 10:00 AM");
            dataManager.addSubject(dataStruct);
            subjects.add(dataStruct);

            Subject algorithms = new Subject("Algorithms", "CS103", 3);
            algorithms.addLecturer("Dr. Sarah Johnson");
            algorithms.setClassroom("CS Lab 3");
            algorithms.setTiming("Wednesday 10:00 AM - 11:00 AM");
            dataManager.addSubject(algorithms);
            subjects.add(algorithms);

            Subject dbSystems = new Subject("Database Systems", "CS104", 3);
            dbSystems.addLecturer("Dr. Sarah Johnson");
            dbSystems.setClassroom("CS Lab 1");
            dbSystems.setTiming("Thursday 11:00 AM - 12:00 PM");
            dataManager.addSubject(dbSystems);
            subjects.add(dbSystems);

            Subject opSystems = new Subject("Operating Systems", "CS105", 3);
            opSystems.addLecturer("Dr. Alex Kumar");
            opSystems.setClassroom("CS Lab 2");
            opSystems.setTiming("Monday 1:00 PM - 2:00 PM");
            dataManager.addSubject(opSystems);
            subjects.add(opSystems);

            Subject compNetworks = new Subject("Computer Networks", "CS106", 3);
            compNetworks.addLecturer("Dr. Alex Kumar");
            compNetworks.setClassroom("CS Lab 3");
            compNetworks.setTiming("Tuesday 2:00 PM - 3:00 PM");
            dataManager.addSubject(compNetworks);
            subjects.add(compNetworks);

            Subject softEng = new Subject("Software Engineering", "CS107", 3);
            softEng.addLecturer("Dr. Alex Kumar");
            softEng.setClassroom("CS Lab 1");
            softEng.setTiming("Wednesday 3:00 PM - 4:00 PM");
            dataManager.addSubject(softEng);
            subjects.add(softEng);

            // Mathematics Department
            Subject calculus = new Subject("Calculus I", "MATH101", 3);
            calculus.addLecturer("Prof. Michael Chen");
            calculus.setClassroom("Class 101");
            calculus.setTiming("Monday 9:00 AM - 10:00 AM");
            dataManager.addSubject(calculus);
            subjects.add(calculus);

            Subject linearAlg = new Subject("Linear Algebra", "MATH102", 3);
            linearAlg.addLecturer("Prof. Michael Chen");
            linearAlg.setClassroom("Class 102");
            linearAlg.setTiming("Tuesday 10:00 AM - 11:00 AM");
            dataManager.addSubject(linearAlg);
            subjects.add(linearAlg);

            Subject numberTheory = new Subject("Number Theory", "MATH103", 3);
            numberTheory.addLecturer("Prof. Michael Chen");
            numberTheory.setClassroom("Class 103");
            numberTheory.setTiming("Wednesday 11:00 AM - 12:00 PM");
            dataManager.addSubject(numberTheory);
            subjects.add(numberTheory);

            Subject diffEq = new Subject("Differential Equations", "MATH104", 3);
            diffEq.addLecturer("Prof. Michael Chen");
            diffEq.setClassroom("Class 104");
            diffEq.setTiming("Thursday 1:00 PM - 2:00 PM");
            dataManager.addSubject(diffEq);
            subjects.add(diffEq);

            Subject probTheory = new Subject("Probability Theory", "MATH105", 3);
            probTheory.addLecturer("Prof. Rachel Green");
            probTheory.setClassroom("Class 201");
            probTheory.setTiming("Friday 2:00 PM - 3:00 PM");
            dataManager.addSubject(probTheory);
            subjects.add(probTheory);

            Subject statistics = new Subject("Statistics", "MATH106", 3);
            statistics.addLecturer("Prof. Rachel Green");
            statistics.setClassroom("Class 202");
            statistics.setTiming("Monday 3:00 PM - 4:00 PM");
            dataManager.addSubject(statistics);
            subjects.add(statistics);

            // Physics Department
            Subject classMech = new Subject("Classical Mechanics", "PHYS101", 3);
            classMech.addLecturer("Dr. Emily Rodriguez");
            classMech.setClassroom("Science Lab 1");
            classMech.setTiming("Tuesday 11:00 AM - 12:00 PM");
            dataManager.addSubject(classMech);
            subjects.add(classMech);

            Subject quantumPhys = new Subject("Quantum Physics", "PHYS102", 3);
            quantumPhys.addLecturer("Dr. Emily Rodriguez");
            quantumPhys.setClassroom("Science Lab 2");
            quantumPhys.setTiming("Wednesday 1:00 PM - 2:00 PM");
            dataManager.addSubject(quantumPhys);
            subjects.add(quantumPhys);

            Subject thermo = new Subject("Thermodynamics", "PHYS103", 3);
            thermo.addLecturer("Dr. Emily Rodriguez");
            thermo.setClassroom("Science Lab 3");
            thermo.setTiming("Thursday 2:00 PM - 3:00 PM");
            dataManager.addSubject(thermo);
            subjects.add(thermo);

            Subject emTheory = new Subject("Electromagnetic Theory", "PHYS104", 3);
            emTheory.addLecturer("Dr. Emily Rodriguez");
            emTheory.setClassroom("Science Lab 1");
            emTheory.setTiming("Friday 3:00 PM - 4:00 PM");
            dataManager.addSubject(emTheory);
            subjects.add(emTheory);

            Subject optics = new Subject("Optics", "PHYS105", 3);
            optics.addLecturer("Dr. Carlos Martinez");
            optics.setClassroom("Science Lab 2");
            optics.setTiming("Monday 8:00 AM - 9:00 AM");
            dataManager.addSubject(optics);
            subjects.add(optics);

            // Chemistry Department
            Subject orgChem = new Subject("Organic Chemistry", "CHEM101", 3);
            orgChem.addLecturer("Prof. James Wilson");
            orgChem.setClassroom("Science Lab 3");
            orgChem.setTiming("Tuesday 9:00 AM - 10:00 AM");
            dataManager.addSubject(orgChem);
            subjects.add(orgChem);

            Subject physChem = new Subject("Physical Chemistry", "CHEM102", 3);
            physChem.addLecturer("Prof. James Wilson");
            physChem.setClassroom("Science Lab 1");
            physChem.setTiming("Wednesday 10:00 AM - 11:00 AM");
            dataManager.addSubject(physChem);
            subjects.add(physChem);

            Subject analChem = new Subject("Analytical Chemistry", "CHEM103", 3);
            analChem.addLecturer("Prof. James Wilson");
            analChem.setClassroom("Science Lab 2");
            analChem.setTiming("Thursday 11:00 AM - 12:00 PM");
            dataManager.addSubject(analChem);
            subjects.add(analChem);

            Subject inorgChem = new Subject("Inorganic Chemistry", "CHEM104", 3);
            inorgChem.addLecturer("Prof. James Wilson");
            inorgChem.setClassroom("Science Lab 3");
            inorgChem.setTiming("Friday 1:00 PM - 2:00 PM");
            dataManager.addSubject(inorgChem);
            subjects.add(inorgChem);

            Subject biochem = new Subject("Biochemistry", "CHEM105", 3);
            biochem.addLecturer("Prof. Anna Smith");
            biochem.setClassroom("Science Lab 1");
            biochem.setTiming("Monday 2:00 PM - 3:00 PM");
            dataManager.addSubject(biochem);
            subjects.add(biochem);

            // Biology Department
            Subject cellBio = new Subject("Cell Biology", "BIO101", 3);
            cellBio.addLecturer("Dr. Lisa Kim");
            cellBio.setClassroom("Science Lab 2");
            cellBio.setTiming("Tuesday 3:00 PM - 4:00 PM");
            dataManager.addSubject(cellBio);
            subjects.add(cellBio);

            Subject genetics = new Subject("Genetics", "BIO102", 3);
            genetics.addLecturer("Dr. Lisa Kim");
            genetics.setClassroom("Science Lab 3");
            genetics.setTiming("Wednesday 8:00 AM - 9:00 AM");
            dataManager.addSubject(genetics);
            subjects.add(genetics);

            Subject microbio = new Subject("Microbiology", "BIO103", 3);
            microbio.addLecturer("Dr. Lisa Kim");
            microbio.setClassroom("Science Lab 1");
            microbio.setTiming("Thursday 9:00 AM - 10:00 AM");
            dataManager.addSubject(microbio);
            subjects.add(microbio);

            Subject ecology = new Subject("Ecology", "BIO104", 3);
            ecology.addLecturer("Dr. Lisa Kim");
            ecology.setClassroom("Science Lab 2");
            ecology.setTiming("Friday 10:00 AM - 11:00 AM");
            dataManager.addSubject(ecology);
            subjects.add(ecology);

            Subject evoBio = new Subject("Evolutionary Biology", "BIO105", 3);
            evoBio.addLecturer("Dr. John Davis");
            evoBio.setClassroom("Science Lab 3");
            evoBio.setTiming("Monday 11:00 AM - 12:00 PM");
            dataManager.addSubject(evoBio);
            subjects.add(evoBio);

            // Electrical Engineering Department
            Subject circuitAnalysis = new Subject("Circuit Analysis", "EE101", 3);
            circuitAnalysis.addLecturer("Prof. David Thompson");
            circuitAnalysis.setClassroom("Engineering Lab 1");
            circuitAnalysis.setTiming("Tuesday 1:00 PM - 2:00 PM");
            dataManager.addSubject(circuitAnalysis);
            subjects.add(circuitAnalysis);

            Subject digitalElec = new Subject("Digital Electronics", "EE102", 3);
            digitalElec.addLecturer("Prof. David Thompson");
            digitalElec.setClassroom("Engineering Lab 2");
            digitalElec.setTiming("Wednesday 2:00 PM - 3:00 PM");
            dataManager.addSubject(digitalElec);
            subjects.add(digitalElec);

            Subject controlSys = new Subject("Control Systems", "EE103", 3);
            controlSys.addLecturer("Prof. David Thompson");
            controlSys.setClassroom("Engineering Lab 3");
            controlSys.setTiming("Thursday 3:00 PM - 4:00 PM");
            dataManager.addSubject(controlSys);
            subjects.add(controlSys);

            Subject powerSys = new Subject("Power Systems", "EE104", 3);
            powerSys.addLecturer("Prof. David Thompson");
            powerSys.setClassroom("Engineering Lab 1");
            powerSys.setTiming("Friday 8:00 AM - 9:00 AM");
            dataManager.addSubject(powerSys);
            subjects.add(powerSys);

            // Mechanical Engineering Department
            Subject statics = new Subject("Statics", "ME101", 3);
            statics.addLecturer("Dr. Maria Garcia");
            statics.setClassroom("Engineering Lab 2");
            statics.setTiming("Monday 9:00 AM - 10:00 AM");
            dataManager.addSubject(statics);
            subjects.add(statics);

            Subject dynamics = new Subject("Dynamics", "ME102", 3);
            dynamics.addLecturer("Dr. Maria Garcia");
            dynamics.setClassroom("Engineering Lab 3");
            dynamics.setTiming("Tuesday 10:00 AM - 11:00 AM");
            dataManager.addSubject(dynamics);
            subjects.add(dynamics);

            Subject fluidMech = new Subject("Fluid Mechanics", "ME103", 3);
            fluidMech.addLecturer("Dr. Maria Garcia");
            fluidMech.setClassroom("Engineering Lab 1");
            fluidMech.setTiming("Wednesday 11:00 AM - 12:00 PM");
            dataManager.addSubject(fluidMech);
            subjects.add(fluidMech);

            Subject machineDesign = new Subject("Machine Design", "ME104", 3);
            machineDesign.addLecturer("Dr. Maria Garcia");
            machineDesign.setClassroom("Engineering Lab 2");
            machineDesign.setTiming("Thursday 1:00 PM - 2:00 PM");
            dataManager.addSubject(machineDesign);
            subjects.add(machineDesign);

            // Civil Engineering Department
            Subject structAnalysis = new Subject("Structural Analysis", "CE101", 3);
            structAnalysis.addLecturer("Prof. Robert Taylor");
            structAnalysis.setClassroom("Engineering Lab 3");
            structAnalysis.setTiming("Friday 2:00 PM - 3:00 PM");
            dataManager.addSubject(structAnalysis);
            subjects.add(structAnalysis);

            Subject geotech = new Subject("Geotechnical Engineering", "CE102", 3);
            geotech.addLecturer("Prof. Robert Taylor");
            geotech.setClassroom("Engineering Lab 1");
            geotech.setTiming("Monday 3:00 PM - 4:00 PM");
            dataManager.addSubject(geotech);
            subjects.add(geotech);

            Subject transEng = new Subject("Transportation Engineering", "CE103", 3);
            transEng.addLecturer("Prof. Robert Taylor");
            transEng.setClassroom("Engineering Lab 2");
            transEng.setTiming("Tuesday 8:00 AM - 9:00 AM");
            dataManager.addSubject(transEng);
            subjects.add(transEng);

            Subject envEng = new Subject("Environmental Engineering", "CE104", 3);
            envEng.addLecturer("Prof. Robert Taylor");
            envEng.setClassroom("Engineering Lab 3");
            envEng.setTiming("Wednesday 9:00 AM - 10:00 AM");
            dataManager.addSubject(envEng);
            subjects.add(envEng);

            // Psychology Department
            Subject cogPsych = new Subject("Cognitive Psychology", "PSYCH101", 3);
            cogPsych.addLecturer("Dr. Jennifer Lee");
            cogPsych.setClassroom("Class 203");
            cogPsych.setTiming("Thursday 10:00 AM - 11:00 AM");
            dataManager.addSubject(cogPsych);
            subjects.add(cogPsych);

            Subject devPsych = new Subject("Developmental Psychology", "PSYCH102", 3);
            devPsych.addLecturer("Dr. Jennifer Lee");
            devPsych.setClassroom("Class 204");
            devPsych.setTiming("Friday 11:00 AM - 12:00 PM");
            dataManager.addSubject(devPsych);
            subjects.add(devPsych);

            Subject clinPsych = new Subject("Clinical Psychology", "PSYCH103", 3);
            clinPsych.addLecturer("Dr. Jennifer Lee");
            clinPsych.setClassroom("Class 101");
            clinPsych.setTiming("Monday 1:00 PM - 2:00 PM");
            dataManager.addSubject(clinPsych);
            subjects.add(clinPsych);

            Subject socPsych = new Subject("Social Psychology", "PSYCH104", 3);
            socPsych.addLecturer("Dr. Jennifer Lee");
            socPsych.setClassroom("Class 102");
            socPsych.setTiming("Tuesday 2:00 PM - 3:00 PM");
            dataManager.addSubject(socPsych);
            subjects.add(socPsych);

            // Economics Department
            Subject microEcon = new Subject("Microeconomics", "ECON101", 3);
            microEcon.addLecturer("Prof. William Brown");
            microEcon.setClassroom("Class 103");
            microEcon.setTiming("Wednesday 3:00 PM - 4:00 PM");
            dataManager.addSubject(microEcon);
            subjects.add(microEcon);

            Subject macroEcon = new Subject("Macroeconomics", "ECON102", 3);
            macroEcon.addLecturer("Prof. William Brown");
            macroEcon.setClassroom("Class 104");
            macroEcon.setTiming("Thursday 8:00 AM - 9:00 AM");
            dataManager.addSubject(macroEcon);
            subjects.add(macroEcon);

            Subject econometrics = new Subject("Econometrics", "ECON103", 3);
            econometrics.addLecturer("Prof. William Brown");
            econometrics.setClassroom("Class 201");
            econometrics.setTiming("Friday 9:00 AM - 10:00 AM");
            dataManager.addSubject(econometrics);
            subjects.add(econometrics);

            Subject intTrade = new Subject("International Trade", "ECON104", 3);
            intTrade.addLecturer("Prof. William Brown");
            intTrade.setClassroom("Class 202");
            intTrade.setTiming("Monday 10:00 AM - 11:00 AM");
            dataManager.addSubject(intTrade);
            subjects.add(intTrade);

            // Additional Computer Science Subjects
            Subject cs108 = new Subject("Web Development", "CS108", 3);
            cs108.addLecturer("Dr. Alex Kumar");
            cs108.setClassroom("CS Lab 1");
            cs108.setTiming("Monday 2:00 PM - 3:00 PM");
            dataManager.addSubject(cs108);
            subjects.add(cs108);

            Subject cs109 = new Subject("Mobile App Development", "CS109", 3);
            cs109.addLecturer("Dr. Alex Kumar");
            cs109.setClassroom("CS Lab 2");
            cs109.setTiming("Tuesday 2:00 PM - 3:00 PM");
            dataManager.addSubject(cs109);
            subjects.add(cs109);

            Subject cs110 = new Subject("Artificial Intelligence", "CS110", 3);
            cs110.addLecturer("Dr. Alex Kumar");
            cs110.setClassroom("CS Lab 3");
            cs110.setTiming("Wednesday 2:00 PM - 3:00 PM");
            dataManager.addSubject(cs110);
            subjects.add(cs110);

            // Mathematics Subjects
            Subject math107 = new Subject("Complex Analysis", "MATH107", 3);
            math107.addLecturer("Prof. Michael Chen");
            math107.setClassroom("Class 101");
            math107.setTiming("Thursday 2:00 PM - 3:00 PM");
            dataManager.addSubject(math107);
            subjects.add(math107);

            Subject math108 = new Subject("Graph Theory", "MATH108", 3);
            math108.addLecturer("Prof. Michael Chen");
            math108.setClassroom("Class 102");
            math108.setTiming("Friday 2:00 PM - 3:00 PM");
            dataManager.addSubject(math108);
            subjects.add(math108);

            // Physics Subjects
            Subject phys106 = new Subject("Nuclear Physics", "PHYS106", 3);
            phys106.addLecturer("Dr. Emily Rodriguez");
            phys106.setClassroom("Science Lab 1");
            phys106.setTiming("Monday 3:00 PM - 4:00 PM");
            dataManager.addSubject(phys106);
            subjects.add(phys106);

            Subject phys107 = new Subject("Astrophysics", "PHYS107", 3);
            phys107.addLecturer("Dr. Emily Rodriguez");
            phys107.setClassroom("Science Lab 2");
            phys107.setTiming("Tuesday 3:00 PM - 4:00 PM");
            dataManager.addSubject(phys107);
            subjects.add(phys107);

            // Chemistry Subjects
            Subject chem106 = new Subject("Polymer Chemistry", "CHEM106", 3);
            chem106.addLecturer("Prof. James Wilson");
            chem106.setClassroom("Science Lab 3");
            chem106.setTiming("Wednesday 3:00 PM - 4:00 PM");
            dataManager.addSubject(chem106);
            subjects.add(chem106);

            Subject chem107 = new Subject("Nuclear Chemistry", "CHEM107", 3);
            chem107.addLecturer("Prof. James Wilson");
            chem107.setClassroom("Science Lab 1");
            chem107.setTiming("Thursday 3:00 PM - 4:00 PM");
            dataManager.addSubject(chem107);
            subjects.add(chem107);

            // Biology Subjects
            Subject bio106 = new Subject("Marine Biology", "BIO106", 3);
            bio106.addLecturer("Dr. Lisa Kim");
            bio106.setClassroom("Science Lab 2");
            bio106.setTiming("Friday 3:00 PM - 4:00 PM");
            dataManager.addSubject(bio106);
            subjects.add(bio106);

            Subject bio107 = new Subject("Immunology", "BIO107", 3);
            bio107.addLecturer("Dr. Lisa Kim");
            bio107.setClassroom("Science Lab 3");
            bio107.setTiming("Monday 4:00 PM - 5:00 PM");
            dataManager.addSubject(bio107);
            subjects.add(bio107);

            // Electrical Engineering Subjects
            Subject ee105 = new Subject("Microelectronics", "EE105", 3);
            ee105.addLecturer("Prof. David Thompson");
            ee105.setClassroom("Engineering Lab 1");
            ee105.setTiming("Tuesday 4:00 PM - 5:00 PM");
            dataManager.addSubject(ee105);
            subjects.add(ee105);

            Subject ee106 = new Subject("Robotics", "EE106", 3);
            ee106.addLecturer("Prof. David Thompson");
            ee106.setClassroom("Engineering Lab 2");
            ee106.setTiming("Wednesday 4:00 PM - 5:00 PM");
            dataManager.addSubject(ee106);
            subjects.add(ee106);

            // Mechanical Engineering Subjects
            Subject me105 = new Subject("Thermodynamics II", "ME105", 3);
            me105.addLecturer("Dr. Maria Garcia");
            me105.setClassroom("Engineering Lab 3");
            me105.setTiming("Thursday 4:00 PM - 5:00 PM");
            dataManager.addSubject(me105);
            subjects.add(me105);

            Subject me106 = new Subject("Robotics and Automation", "ME106", 3);
            me106.addLecturer("Dr. Maria Garcia");
            me106.setClassroom("Engineering Lab 1");
            me106.setTiming("Friday 4:00 PM - 5:00 PM");
            dataManager.addSubject(me106);
            subjects.add(me106);

            // Civil Engineering Subjects
            Subject ce105 = new Subject("Construction Management", "CE105", 3);
            ce105.addLecturer("Prof. Robert Taylor");
            ce105.setClassroom("Engineering Lab 2");
            ce105.setTiming("Monday 1:00 PM - 2:00 PM");
            dataManager.addSubject(ce105);
            subjects.add(ce105);

            Subject ce106 = new Subject("Earthquake Engineering", "CE106", 3);
            ce106.addLecturer("Prof. Robert Taylor");
            ce106.setClassroom("Engineering Lab 3");
            ce106.setTiming("Tuesday 1:00 PM - 2:00 PM");
            dataManager.addSubject(ce106);
            subjects.add(ce106);

            // Psychology Subjects
            Subject psych105 = new Subject("Behavioral Psychology", "PSYCH105", 3);
            psych105.addLecturer("Dr. Jennifer Lee");
            psych105.setClassroom("Class 103");
            psych105.setTiming("Wednesday 1:00 PM - 2:00 PM");
            dataManager.addSubject(psych105);
            subjects.add(psych105);

            Subject psych106 = new Subject("Educational Psychology", "PSYCH106", 3);
            psych106.addLecturer("Dr. Jennifer Lee");
            psych106.setClassroom("Class 104");
            psych106.setTiming("Thursday 1:00 PM - 2:00 PM");
            dataManager.addSubject(psych106);
            subjects.add(psych106);

            // Economics Subjects
            Subject econ105 = new Subject("Development Economics", "ECON105", 3);
            econ105.addLecturer("Prof. William Brown");
            econ105.setClassroom("Class 201");
            econ105.setTiming("Friday 1:00 PM - 2:00 PM");
            dataManager.addSubject(econ105);
            subjects.add(econ105);

            Subject econ106 = new Subject("Financial Economics", "ECON106", 3);
            econ106.addLecturer("Prof. William Brown");
            econ106.setClassroom("Class 202");
            econ106.setTiming("Monday 11:00 AM - 12:00 PM");
            dataManager.addSubject(econ106);
            subjects.add(econ106);
        }
    }

    private void setupGUI() {
        setTitle("Admin Portal - Welcome " + adminUsername);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 650);
        setLocationRelativeTo(null);

        // Create main panel with border layout
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        // Create header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(50, 50, 50));
        headerPanel.setPreferredSize(new Dimension(getWidth(), 60));
        
        // Add welcome label
        JLabel welcomeLabel = new JLabel("Welcome, " + adminUsername);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        
        // Add logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(220, 53, 69));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setBorderPainted(false);
        logoutButton.setPreferredSize(new Dimension(100, 40));
        logoutButton.setMargin(new Insets(5, 10, 5, 10));
        logoutButton.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to logout?",
                "Logout Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            if (choice == JOptionPane.YES_OPTION) {
                dispose();
            }
        });
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setOpaque(false);
        logoutPanel.add(logoutButton);
        headerPanel.add(logoutPanel, BorderLayout.EAST);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        
        // Create tabs
        tabbedPane.addTab("Classrooms", createClassroomPanel());
        tabbedPane.addTab("Teachers", createTeacherPanel());
        tabbedPane.addTab("Subjects", createSubjectPanel());

        mainPanel.add(tabbedPane, BorderLayout.CENTER);
        
        // Add status bar
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(new Color(240, 240, 240));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel(" Ready");
        statusLabel.setBorder(BorderFactory.createEmptyBorder(3, 10, 3, 0));
        statusBar.add(statusLabel, BorderLayout.WEST);
        mainPanel.add(statusBar, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createClassroomPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table model
        String[] columns = {"Room Number", "Capacity", "Audio/Video", "Current Subject", "Current Teacher"};
        classroomModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        classroomTable = new JTable(classroomModel);
        classroomTable.setRowHeight(25);
        classroomTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(classroomTable);
        
        // Add data to table
        for (Classroom classroom : classrooms) {
            classroomModel.addRow(new Object[]{
                classroom.getRoomNumber(),
                classroom.getCapacity(),
                classroom.hasAudioVideo() ? "Yes" : "No",
                classroom.getCurrentSubject(),
                classroom.getCurrentTeacher()
            });
        }

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Classroom");
        JButton editButton = new JButton("Edit Classroom");
        JButton deleteButton = new JButton("Delete Classroom");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> showAddClassroomDialog());
        editButton.addActionListener(e -> showEditClassroomDialog());
        deleteButton.addActionListener(e -> deleteSelectedClassroom());
        refreshButton.addActionListener(e -> refreshClassroomTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void showAddClassroomDialog() {
        JDialog dialog = new JDialog(this, "Add New Classroom", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Room Number
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Room Number:"), gbc);
        gbc.gridx = 1;
        JTextField roomField = new JTextField(20);
        panel.add(roomField, gbc);
        
        // Capacity
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        JTextField capacityField = new JTextField(20);
        panel.add(capacityField, gbc);
        
        // Audio/Video
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Audio/Video:"), gbc);
        gbc.gridx = 1;
        JCheckBox avCheckBox = new JCheckBox("Has Audio/Video");
        panel.add(avCheckBox, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                String roomNumber = roomField.getText().trim();
                int capacity = Integer.parseInt(capacityField.getText().trim());
                boolean hasAV = avCheckBox.isSelected();
                
                if (roomNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Room number cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check if room number already exists
                for (Classroom c : classrooms) {
                    if (c.getRoomNumber().equals(roomNumber)) {
                        JOptionPane.showMessageDialog(dialog, "Room number already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                // Add new classroom
                Classroom newClassroom = new Classroom(roomNumber, capacity, hasAV);
                dataManager.addClassroom(newClassroom);
                classrooms.add(newClassroom);
                
                // Add to table
                classroomModel.addRow(new Object[]{
                    newClassroom.getRoomNumber(),
                    newClassroom.getCapacity(),
                    newClassroom.hasAudioVideo() ? "Yes" : "No",
                    newClassroom.getCurrentSubject(),
                    newClassroom.getCurrentTeacher()
                });
                
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Classroom added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Capacity must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void showEditClassroomDialog() {
        int selectedRow = classroomTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a classroom to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Classroom currentClassroom = classrooms.get(selectedRow);
        
        JDialog dialog = new JDialog(this, "Edit Classroom", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Room Number
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Room Number:"), gbc);
        gbc.gridx = 1;
        JTextField roomField = new JTextField(currentClassroom.getRoomNumber(), 20);
        panel.add(roomField, gbc);
        
        // Capacity
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        JTextField capacityField = new JTextField(String.valueOf(currentClassroom.getCapacity()), 20);
        panel.add(capacityField, gbc);
        
        // Audio/Video
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Audio/Video:"), gbc);
        gbc.gridx = 1;
        JCheckBox avCheckBox = new JCheckBox("Has Audio/Video", currentClassroom.hasAudioVideo());
        panel.add(avCheckBox, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                String roomNumber = roomField.getText().trim();
                int capacity = Integer.parseInt(capacityField.getText().trim());
                boolean hasAV = avCheckBox.isSelected();
                
                if (roomNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Room number cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Check if room number already exists (excluding current room)
                for (int i = 0; i < classrooms.size(); i++) {
                    if (i != selectedRow && classrooms.get(i).getRoomNumber().equals(roomNumber)) {
                        JOptionPane.showMessageDialog(dialog, "Room number already exists", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
                
                // Create new classroom with updated values
                Classroom updatedClassroom = new Classroom(roomNumber, capacity, hasAV);
                dataManager.updateClassroom(selectedRow, updatedClassroom);
                classrooms.set(selectedRow, updatedClassroom);
                
                // Update table
                classroomModel.setValueAt(roomNumber, selectedRow, 0);
                classroomModel.setValueAt(capacity, selectedRow, 1);
                classroomModel.setValueAt(hasAV ? "Yes" : "No", selectedRow, 2);
                
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Classroom updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Capacity must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    private void deleteSelectedClassroom() {
        int selectedRow = classroomTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a classroom to delete", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Are you sure you want to delete this classroom?",
            "Delete Confirmation",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            dataManager.deleteClassroom(selectedRow);
            classrooms.remove(selectedRow);
            classroomModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Classroom deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void refreshClassroomTable() {
        classroomModel.setRowCount(0);
        for (Classroom classroom : classrooms) {
            classroomModel.addRow(new Object[]{
                classroom.getRoomNumber(),
                classroom.getCapacity(),
                classroom.hasAudioVideo() ? "Yes" : "No",
                classroom.getCurrentSubject(),
                classroom.getCurrentTeacher()
            });
        }
    }

    private JPanel createTeacherPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table model
        String[] columns = {"Name", "Department", "Subjects", "Email", "Phone"};
        teacherModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        teacherTable = new JTable(teacherModel);
        teacherTable.setRowHeight(25);
        teacherTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(teacherTable);
        
        // Add data to table
        for (Teacher teacher : teachers) {
            teacherModel.addRow(new Object[]{
                teacher.getName(),
                teacher.getDepartment(),
                String.join(", ", teacher.getSubjects()),
                teacher.getEmail(),
                teacher.getPhone()
            });
        }

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Teacher");
        JButton editButton = new JButton("Edit Teacher");
        JButton deleteButton = new JButton("Delete Teacher");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> showAddTeacherDialog());
        editButton.addActionListener(e -> showEditTeacherDialog());
        deleteButton.addActionListener(e -> deleteSelectedTeacher());
        refreshButton.addActionListener(e -> refreshTeacherTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void showAddTeacherDialog() {
        JDialog dialog = new JDialog(this, "Add Teacher", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        panel.add(nameField, gbc);
        
        // Department
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        JTextField deptField = new JTextField(20);
        panel.add(deptField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(20);
        panel.add(phoneField, gbc);
        
        // Subjects
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Subjects:"), gbc);
        gbc.gridx = 1;
        JTextField subjectsField = new JTextField(20);
        subjectsField.setToolTipText("Enter subjects separated by commas");
        panel.add(subjectsField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String department = deptField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String subjectsText = subjectsField.getText().trim();
            
            if (name.isEmpty() || department.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create new teacher
            Teacher newTeacher = new Teacher(name, department, email, phone);
            
            // Add subjects
            if (!subjectsText.isEmpty()) {
                String[] subjectArray = subjectsText.split(",");
                for (String subject : subjectArray) {
                    newTeacher.addSubject(subject.trim());
                }
            }
            
            // Add to list
            dataManager.addTeacher(newTeacher);
            teachers.add(newTeacher);
            
            // Add to table
            teacherModel.addRow(new Object[]{
                newTeacher.getName(),
                newTeacher.getDepartment(),
                String.join(", ", newTeacher.getSubjects()),
                newTeacher.getEmail(),
                newTeacher.getPhone()
            });
            
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Teacher added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditTeacherDialog() {
        int selectedRow = teacherTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a teacher to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        final Teacher currentTeacher = teachers.get(selectedRow);
        
        JDialog dialog = new JDialog(this, "Edit Teacher", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(currentTeacher.getName(), 20);
        panel.add(nameField, gbc);
        
        // Department
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1;
        JTextField deptField = new JTextField(currentTeacher.getDepartment(), 20);
        panel.add(deptField, gbc);
        
        // Email
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        JTextField emailField = new JTextField(currentTeacher.getEmail(), 20);
        panel.add(emailField, gbc);
        
        // Phone
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Phone:"), gbc);
        gbc.gridx = 1;
        JTextField phoneField = new JTextField(currentTeacher.getPhone(), 20);
        panel.add(phoneField, gbc);
        
        // Subjects
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Subjects:"), gbc);
        gbc.gridx = 1;
        JTextField subjectsField = new JTextField(String.join(", ", currentTeacher.getSubjects()), 20);
        subjectsField.setToolTipText("Enter subjects separated by commas");
        panel.add(subjectsField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String department = deptField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String subjectsText = subjectsField.getText().trim();
            
            if (name.isEmpty() || department.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create new teacher
            Teacher updatedTeacher = new Teacher(name, department, email, phone);
            
            // Add subjects
            if (!subjectsText.isEmpty()) {
                String[] subjectArray = subjectsText.split(",");
                for (String subject : subjectArray) {
                    updatedTeacher.addSubject(subject.trim());
                }
            }
            
            // Update list
            dataManager.updateTeacher(selectedRow, updatedTeacher);
            teachers.set(selectedRow, updatedTeacher);
            
            // Update table
            teacherModel.setValueAt(updatedTeacher.getName(), selectedRow, 0);
            teacherModel.setValueAt(updatedTeacher.getDepartment(), selectedRow, 1);
            teacherModel.setValueAt(String.join(", ", updatedTeacher.getSubjects()), selectedRow, 2);
            teacherModel.setValueAt(updatedTeacher.getEmail(), selectedRow, 3);
            teacherModel.setValueAt(updatedTeacher.getPhone(), selectedRow, 4);
            
            dialog.dispose();
            JOptionPane.showMessageDialog(this, "Teacher updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void deleteSelectedTeacher() {
        int selectedRow = teacherTable.getSelectedRow();
        if (selectedRow >= 0) {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this teacher?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                dataManager.deleteTeacher(selectedRow);
                teachers.remove(selectedRow);
                teacherModel.removeRow(selectedRow);
                JOptionPane.showMessageDialog(this, "Teacher deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void refreshTeacherTable() {
        DefaultTableModel model = (DefaultTableModel) teacherTable.getModel();
        model.setRowCount(0);
        for (Teacher teacher : teachers) {
            model.addRow(new Object[]{
                teacher.getName(),
                teacher.getDepartment(),
                teacher.getEmail(),
                teacher.getPhone()
            });
        }
    }

    private JPanel createSubjectPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Create table model
        String[] columns = {"Name", "Code", "Lecturers", "Classroom", "Timing", "Credits"};
        subjectModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        subjectTable = new JTable(subjectModel);
        subjectTable.setRowHeight(25);
        subjectTable.getTableHeader().setReorderingAllowed(false);
        JScrollPane scrollPane = new JScrollPane(subjectTable);
        
        // Add data to table
        refreshSubjectTable();  // Use the refresh method to populate the table

        // Add buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addButton = new JButton("Add Subject");
        JButton editButton = new JButton("Edit Subject");
        JButton deleteButton = new JButton("Delete Subject");
        JButton refreshButton = new JButton("Refresh");
        
        addButton.addActionListener(e -> showAddSubjectDialog());
        editButton.addActionListener(e -> showEditSubjectDialog());
        deleteButton.addActionListener(e -> deleteSelectedSubject());
        refreshButton.addActionListener(e -> refreshSubjectTable());
        
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void showAddSubjectDialog() {
        JDialog dialog = new JDialog(this, "Add Subject", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(20);
        panel.add(nameField, gbc);
        
        // Code
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Code:"), gbc);
        gbc.gridx = 1;
        JTextField codeField = new JTextField(20);
        panel.add(codeField, gbc);
        
        // Credits
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        JTextField creditsField = new JTextField(20);
        panel.add(creditsField, gbc);
        
        // Lecturers
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Lecturers:"), gbc);
        gbc.gridx = 1;
        JTextField lecturersField = new JTextField(20);
        lecturersField.setToolTipText("Enter lecturer names separated by commas");
        panel.add(lecturersField, gbc);
        
        // Classroom
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Classroom:"), gbc);
        gbc.gridx = 1;
        JTextField classroomField = new JTextField(20);
        panel.add(classroomField, gbc);
        
        // Timing
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Timing:"), gbc);
        gbc.gridx = 1;
        JTextField timingField = new JTextField(20);
        panel.add(timingField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String code = codeField.getText().trim();
                int credits = Integer.parseInt(creditsField.getText().trim());
                String lecturersText = lecturersField.getText().trim();
                String classroom = classroomField.getText().trim();
                String timing = timingField.getText().trim();
                
                if (name.isEmpty() || code.isEmpty() || classroom.isEmpty() || timing.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create new subject
                Subject newSubject = new Subject(name, code, credits);
                
                // Add lecturers
                if (!lecturersText.isEmpty()) {
                    String[] lecturerArray = lecturersText.split(",");
                    for (String lecturer : lecturerArray) {
                        newSubject.addLecturer(lecturer.trim());
                    }
                }
                
                newSubject.setClassroom(classroom);
                newSubject.setTiming(timing);
                
                // Add to DataManager
                dataManager.addSubject(newSubject);
                
                // Add to local list
                subjects.add(newSubject);
                
                // Add to table
                subjectModel.addRow(new Object[]{
                    newSubject.getName(),
                    newSubject.getCode(),
                    String.join(", ", newSubject.getLecturers()),
                    newSubject.getClassroom(),
                    newSubject.getTiming(),
                    newSubject.getCredits()
                });
                
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Subject added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Credits must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEditSubjectDialog() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a subject to edit", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Subject currentSubject = subjects.get(selectedRow);
        
        JDialog dialog = new JDialog(this, "Edit Subject", true);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Name
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        JTextField nameField = new JTextField(currentSubject.getName(), 20);
        panel.add(nameField, gbc);
        
        // Code
        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Code:"), gbc);
        gbc.gridx = 1;
        JTextField codeField = new JTextField(currentSubject.getCode(), 20);
        panel.add(codeField, gbc);
        
        // Credits
        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Credits:"), gbc);
        gbc.gridx = 1;
        JTextField creditsField = new JTextField(String.valueOf(currentSubject.getCredits()), 20);
        panel.add(creditsField, gbc);
        
        // Lecturers
        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Lecturers:"), gbc);
        gbc.gridx = 1;
        JTextField lecturersField = new JTextField(String.join(", ", currentSubject.getLecturers()), 20);
        lecturersField.setToolTipText("Enter lecturer names separated by commas");
        panel.add(lecturersField, gbc);
        
        // Classroom
        gbc.gridx = 0; gbc.gridy = 4;
        panel.add(new JLabel("Classroom:"), gbc);
        gbc.gridx = 1;
        JTextField classroomField = new JTextField(currentSubject.getClassroom(), 20);
        panel.add(classroomField, gbc);
        
        // Timing
        gbc.gridx = 0; gbc.gridy = 5;
        panel.add(new JLabel("Timing:"), gbc);
        gbc.gridx = 1;
        JTextField timingField = new JTextField(currentSubject.getTiming(), 20);
        panel.add(timingField, gbc);
        
        // Buttons
        gbc.gridx = 0; gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        
        saveButton.addActionListener(e -> {
            try {
                String name = nameField.getText().trim();
                String code = codeField.getText().trim();
                int credits = Integer.parseInt(creditsField.getText().trim());
                String lecturersText = lecturersField.getText().trim();
                String classroom = classroomField.getText().trim();
                String timing = timingField.getText().trim();
                
                if (name.isEmpty() || code.isEmpty() || classroom.isEmpty() || timing.isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Create new subject
                Subject updatedSubject = new Subject(name, code, credits);
                
                // Add lecturers
                if (!lecturersText.isEmpty()) {
                    String[] lecturerArray = lecturersText.split(",");
                    for (String lecturer : lecturerArray) {
                        updatedSubject.addLecturer(lecturer.trim());
                    }
                }
                
                updatedSubject.setClassroom(classroom);
                updatedSubject.setTiming(timing);
                
                // Update in DataManager
                dataManager.updateSubject(selectedRow, updatedSubject);
                
                // Update in local list
                subjects.set(selectedRow, updatedSubject);
                
                // Update table
                subjectModel.setValueAt(updatedSubject.getName(), selectedRow, 0);
                subjectModel.setValueAt(updatedSubject.getCode(), selectedRow, 1);
                subjectModel.setValueAt(String.join(",", updatedSubject.getLecturers()), selectedRow, 2);
                subjectModel.setValueAt(updatedSubject.getClassroom(), selectedRow, 3);
                subjectModel.setValueAt(updatedSubject.getTiming(), selectedRow, 4);
                subjectModel.setValueAt(updatedSubject.getCredits(), selectedRow, 5);
                
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Subject updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Credits must be a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> dialog.dispose());
        
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        panel.add(buttonPanel, gbc);
        
        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void deleteSelectedSubject() {
        int selectedRow = subjectTable.getSelectedRow();
        if (selectedRow >= 0) {
            int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this subject?",
                "Delete Confirmation",
                JOptionPane.YES_NO_OPTION
            );
            
            if (choice == JOptionPane.YES_OPTION) {
                // Delete from DataManager
                dataManager.deleteSubject(selectedRow);
                
                // Delete from local list
                subjects.remove(selectedRow);
                
                // Delete from table
                subjectModel.removeRow(selectedRow);
                
                JOptionPane.showMessageDialog(this, "Subject deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void refreshSubjectTable() {
        subjectModel.setRowCount(0);
        for (Subject subject : subjects) {
            subjectModel.addRow(new Object[]{
                subject.getName(),
                subject.getCode(),
                String.join(", ", subject.getLecturers()),
                subject.getClassroom(),
                subject.getTiming(),
                subject.getCredits()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AdminPortal().setVisible(true);
        });
    }
} 