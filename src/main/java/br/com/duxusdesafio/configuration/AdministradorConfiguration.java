package br.com.duxusdesafio.configuration;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import br.com.duxusdesafio.model.ComposicaoTime;
import br.com.duxusdesafio.model.Integrante;
import br.com.duxusdesafio.model.Time;
import br.com.duxusdesafio.repositories.ComposicaoTimeRepository;
import br.com.duxusdesafio.repositories.IntegranteRepository;
import br.com.duxusdesafio.repositories.TimeRepository;

@Configuration
@Profile("Administrator")
public class AdministradorConfiguration implements CommandLineRunner{
	
	@Autowired
	private IntegranteRepository integranteRepository;
	
	@Autowired
	private ComposicaoTimeRepository composicaoTimeRepository;
	
	@Autowired
	private TimeRepository timeRepository;

	@Override
	public void run(String... args) throws Exception {

		String franquiaNBA = "NBA"; 
        LocalDate data1993 = LocalDate.of(1993, 1, 1);
        LocalDate data1994 = LocalDate.of(1994, 1, 1);
        LocalDate data1995 = LocalDate.of(1995, 1, 1);

        List<ComposicaoTime> composicaoTime1993 = new ArrayList<>();
        List<ComposicaoTime> composicaoTime1994 = new ArrayList<>();
        List<ComposicaoTime> composicaoTime1995 = new ArrayList<>();
        List<ComposicaoTime> composicaoTime1994E1995 = new ArrayList<>();

        Integrante michael_jordan = new Integrante(franquiaNBA, "Michael Jordan", "ala", composicaoTime1994E1995);
        Integrante denis_rodman = new Integrante(franquiaNBA, "Denis Rodman", "ala-pivô", composicaoTime1995);
        Integrante scottie_pippen = new Integrante(franquiaNBA, "Scottie Pippen", "ala", composicaoTime1995);

        integranteRepository.save(michael_jordan);
        integranteRepository.save(denis_rodman);
        integranteRepository.save(scottie_pippen);

        Time timeChicagoBullsDe1994 = new Time(data1994, composicaoTime1994);
        Time timeChicagoBullsDe1995 = new Time(data1995, composicaoTime1995);
        Time timeDetroidPistonsDe1993 = new Time(data1993, composicaoTime1993);

        timeRepository.save(timeChicagoBullsDe1994);
        timeRepository.save(timeChicagoBullsDe1995);
        timeRepository.save(timeDetroidPistonsDe1993);

        composicaoTime1994.add(new ComposicaoTime(timeChicagoBullsDe1994, michael_jordan));
        composicaoTime1994.add(new ComposicaoTime(timeChicagoBullsDe1994, denis_rodman));
        composicaoTime1994.add(new ComposicaoTime(timeChicagoBullsDe1994, scottie_pippen));

        composicaoTime1995.add(new ComposicaoTime(timeChicagoBullsDe1995, michael_jordan));
        composicaoTime1995.add(new ComposicaoTime(timeChicagoBullsDe1995, denis_rodman));
        composicaoTime1995.add(new ComposicaoTime(timeChicagoBullsDe1995, scottie_pippen));

        composicaoTime1993.add(new ComposicaoTime(timeDetroidPistonsDe1993, denis_rodman));

        composicaoTimeRepository.saveAll(composicaoTime1994);
        composicaoTimeRepository.saveAll(composicaoTime1995);
        composicaoTimeRepository.saveAll(composicaoTime1993);
	} 
}
