package com.rs.springframework.services;

import com.rs.springframework.commands.UnitOfMeasureCommand;
import com.rs.springframework.converters.UnitOfMeasureToUnitOfMeasureCommand;
import com.rs.springframework.domain.UnitOfMeasure;
import com.rs.springframework.repositories.UnitOfMeasureRepository;
import com.rs.springframework.repositories.reactive.UnitOfMeasureReactiveRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;

import java.util.HashSet;
import java.util.List;
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
    UnitOfMeasureReactiveRepository unitOfMeasureReactiveRepository;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        service = new UnitOfMeasureServiceImpl(unitOfMeasureReactiveRepository,unitOfMeasureToUnitOfMeasureCommand);
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
        when(unitOfMeasureReactiveRepository.findAll()).thenReturn(Flux.just(uom1,uom2));

        List<UnitOfMeasureCommand> commands =service.listAllUoms().collectList().block();

        //then
        assertEquals(2,commands.size());
        verify(unitOfMeasureReactiveRepository,times(1)).findAll();

    }
}