package com.lambdaschool.schools.services;

import com.lambdaschool.schools.exceptions.ResourceNotFoundException;
import com.lambdaschool.schools.models.AdviceData;
import com.lambdaschool.schools.models.Course;
import com.lambdaschool.schools.models.Instructor;
import com.lambdaschool.schools.repositories.CourseRepository;
import com.lambdaschool.schools.repositories.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service(value = "instructorService")
public class InstructorServiceImpl implements InstructorService
{
    public static AdviceData ourAdvice = new AdviceData();

    @Autowired
    private InstructorRepository instructorRepository;

    @Autowired
    private CoursesService coursesService;
    public static AdviceData ourAdviceData = new AdviceData();

    @Override
    public Instructor findInstructorByid(long id)
    {
        Instructor instructor = instructorRepository.findById(id)
            .orElseThrow(()-> new ResourceNotFoundException("Instructor "+ id + " not found!"));
        return instructor;
    }

    @Transactional
    @Override
    public Instructor save(Instructor instructor)
    {
        System.out.println(instructor);
        Instructor newInstructor = new Instructor();
        if(instructor.getInstructorid()!= 0)
        {
            instructorRepository.findById(instructor.getInstructorid())
                .orElseThrow(() -> new ResourceNotFoundException("Instructor " + instructor.getInstructorid() + " not found!"));
            newInstructor.setInstructorid(instructor.getInstructorid());
        }

       newInstructor.setName(instructor.getName());
       newInstructor.setAdvice(instructor.getAdvice());

       newInstructor.getCourses().clear();
//
       for(Course c: instructor.getCourses())
       {
           Course course = coursesService.findCourseById(c.getCourseid());
           newInstructor.getCourses().add(course);
       }

        return instructorRepository.save(newInstructor);
    }

    @Transactional
    @Override
    public Instructor addAdvice(long id)
    {
       Instructor newInst = new Instructor();
       Instructor instructor = instructorRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Instructor "+id+" not found!"));

       newInst.setInstructorid(id);
       newInst.setName(instructor.getName());

        AdviceData adviceData = new AdviceData();
        adviceData = ourAdviceData;
       newInst.setAdvice(adviceData.getSlip().get("advice"));

       newInst.getCourses().clear();
       for(Course c: instructor.getCourses())
       {
           Course course = coursesService.findCourseById(c.getCourseid());
           newInst.getCourses().add(course);
       }
        System.out.println(newInst);
     return instructorRepository.save(newInst);
    }
}
