package com.guaitilsoft.utils;

import com.guaitilsoft.models.*;
import com.guaitilsoft.models.constant.Gender;
import com.guaitilsoft.models.constant.LocalType;
import com.guaitilsoft.models.constant.MemberType;
import com.guaitilsoft.models.constant.PersonType;

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
        local.setAddress(UtilsTest.createBasicAddress());
        local.setName("Local Prueba");
        local.setDescription("Mi bello local");
        local.setLocalType(LocalType.WORKSHOP);
        local.setTelephone("88888888");
        return local;
    }

    public static Address createBasicAddress() {
        Address address = new Address();
        address.setPhysicalAddress("Hojancha");
        address.setVirtualAddress(UtilsTest.createBasicVirtualAddress());
        return address;
    }

    private static VirtualAddress createBasicVirtualAddress() {
        VirtualAddress virtualAddress = new VirtualAddress();
        virtualAddress.setLatitude("123431234");
        virtualAddress.setLongitude("12312312");
        return virtualAddress;
    }

}
