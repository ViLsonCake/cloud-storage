package com.project.CloudStorage.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileUtilsTest {

    @Test
    void convertToReadableSize() {
        assertEquals(FileUtils.convertToReadableSize(17397L), "16KB");
        assertEquals(FileUtils.convertToReadableSize(200L), "200B");
        assertEquals(FileUtils.convertToReadableSize(49366376L), "47,1MB");
    }

    @Test
    void getUsernameFromHeader() {
        assertEquals(FileUtils.getUsernameFromHeader("Basic ZmFrZUFkbWluOjAxMDEwMTAx"), "fakeAdmin");
        assertEquals(FileUtils.getUsernameFromHeader("Basic VmlMc29uQ2FrZTpxd2VydHk="), "ViLsonCake");
        assertEquals(FileUtils.getUsernameFromHeader("Basic SHdlc3VzOjEyMzQ1"), "Hwesus");
    }
}