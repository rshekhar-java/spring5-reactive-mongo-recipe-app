package com.rs.springframework.converters;

import com.rs.springframework.commands.UnitOfMeasureCommand;
import com.rs.springframework.domain.UnitOfMeasure;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * created by rs 1/11/2022.
 */
public class UnitOfMeasureToUnitOfMeasureCommandTest {

    public static final String DESCRIPTION = "description";
    public static final String LONG_VALUE = "1";

    UnitOfMeasureToUnitOfMeasureCommand converter;


    @Before
    public void setUp() throws Exception {
        converter = new UnitOfMeasureToUnitOfMeasureCommand();
    }

    @Test
    public void testNullObjectConvert() throws Exception {
        assertNull(converter.convert(null));
    }

    @Test
    public void testEmptyObj() throws Exception {
        assertNotNull(converter.convert(new UnitOfMeasure()));
    }

    @Test
    public void convert() throws Exception {
        //given
        UnitOfMeasure uom = new UnitOfMeasure();
        uom.setId(LONG_VALUE);
        uom.setDescription(DESCRIPTION);
        //when
        UnitOfMeasureCommand uomc = converter.convert(uom);

        //then
        assertEquals(LONG_VALUE, uomc.getId());
        assertEquals(DESCRIPTION, uomc.getDescription());
    }
}