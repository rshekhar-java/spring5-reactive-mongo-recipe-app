package com.rs.springframework.services;

import com.rs.springframework.commands.UnitOfMeasureCommand;
import com.rs.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.rs.springframework.domain.UnitOfMeasure;
import com.rs.springframework.repositories.UnitOfMeasureRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * created by rs 1/13/2022.
 */
public class UnitOfMeasureServiceImplTest {

    UnitOfMeasureToUnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand = new UnitOfMeasureToUnitOfMeasureCommand();

    UnitOfMeasureService service;
    @Mock
    UnitOfMeasureRepository unitOfMeasureRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new UnitOfMeasureServiceImpl(unitOfMeasureRepository,unitOfMeasureToUnitOfMeasureCommand);
    }

    @Test
    public void listAllUoms() throws Exception{
        //given
        Set<UnitOfMeasure> unitOfMeasure = new HashSet<>();
        UnitOfMeasure uom1 =new UnitOfMeasure();

        uom1.setId("1");
        unitOfMeasure.add(uom1);

        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId("2");
        unitOfMeasure.add(uom2);

        //when
        when(unitOfMeasureRepository.findAll()).thenReturn(unitOfMeasure);

        Set<UnitOfMeasureCommand> commands =service.listAllUoms();

        //then
        assertEquals(2,commands.size());
        verify(unitOfMeasureRepository,times(1)).findAll();

    }
}