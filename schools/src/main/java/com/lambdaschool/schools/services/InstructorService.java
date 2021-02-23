package com.lambdaschool.schools.services;

import com.lambdaschool.schools.models.Instructor;

public interface InstructorService
{
    Instructor save(Instructor instructor);
    Instructor addAdvice(long id);
    Instructor findInstructorByid(long id);
}
