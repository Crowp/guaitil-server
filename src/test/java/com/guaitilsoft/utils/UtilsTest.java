package com.guaitilsoft.utils;

import com.guaitilsoft.models.*;
import com.guaitilsoft.models.constant.*;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class UtilsTest {

    public static Person createBasicPerson() {
        Person person = new Person();
        person.setId("901110534");
        person.setName("Ricardo");
        person.setFirstLastName("Morataya");
        person.setSecondLastName("Sandoval");
        person.setGender(Gender.MALE);
        person.setTelephone("85807271");
        person.setEmail("ricardojms1999@gmail.com");
        person.setPersonType(PersonType.ROLE_MEMBER);
        return person;
    }

    public static Member createBasicMember() {
        Member member = new Member();
        member.setPerson(UtilsTest.createBasicPerson());
        member.setOccupation("Programador");
        member.setMemberType(MemberType.ASSOCIATED);
        member.setLocals(new ArrayList<>());
        return member;
    }

    public static Local createBasicLocal() {
        Local local = new Local();
        local.setLocalDescription(UtilsTest.createBasicLocalDescription());
        return local;
    }

    public static LocalDescription createBasicLocalDescription(){
        LocalDescription localDescription = new LocalDescription();
        localDescription.setAddress(UtilsTest.createBasicAddress());
        localDescription.setLocalName("Local Prueba");
        localDescription.setDescription("Mi bello local");
        localDescription.setLocalType(LocalType.WORKSHOP);
        localDescription.setLocalTelephone("88888888");
        return localDescription;
    }

    public static Activity createBasicActivity() {
        Activity activity = new Activity();
        activity.setActivityDescription(UtilsTest.createActivityDescriptionBasic());
        return activity;
    }

    public static ActivityDescription createActivityDescriptionBasic(){
        ActivityDescription activityDescription =  new ActivityDescription();
        activityDescription.setActivityDate(LocalDateTime.now());
        activityDescription.setActivityType(ActivityType.EXPERIENCE);
        activityDescription.setName("Una actividad de prueba");
        activityDescription.setDescription("Una descripcion de prueba");
        activityDescription.setAddress(UtilsTest.createBasicAddress());
        return activityDescription;
    }

    public static Address createBasicAddress() {
        Address address = new Address();
        address.setPhysicalAddress("Hojancha");
        return address;
    }

}
