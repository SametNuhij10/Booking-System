package Services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import Entity.Provider;
import Entity.Availability;
import Repositories.ProviderRepository;
import Repositories.AvailabilityRepository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@ApplicationScoped
public class ProviderService {

    @Inject
    private ProviderRepository providerRepository;

    @Inject
    private AvailabilityRepository availabilityRepository;

    public Provider findProviderById(Long id) {
        return providerRepository.findById(id);
    }

    public List<Provider> getAllProviders() {
        return providerRepository.findAll();
    }

    @Transactional
    public Provider createProvider(Provider provider) {
        return providerRepository.create(provider);
    }

    @Transactional
    public Provider updateProvider(Provider provider) {
        return providerRepository.update(provider);
    }

    @Transactional
    public void deleteProvider(Provider provider) {
        providerRepository.delete(provider);
    }

    @Transactional
    public Availability addAvailability(Long providerId,
                                        LocalDate date,
                                        LocalTime startTime,
                                        LocalTime endTime) {
        Availability availability = new Availability(
                providerId.intValue(),
                date,
                startTime,
                endTime
        );
        return availabilityRepository.create(availability);
    }


    @Transactional
    public void addWeeklyAvailability(Long providerId,
                                      DayOfWeek dayOfWeek,
                                      LocalDate fromDate,
                                      LocalDate toDate,
                                      LocalTime startTime,
                                      LocalTime endTime) {
        LocalDate d = fromDate;
        while (!d.isAfter(toDate)) {
            if (d.getDayOfWeek() == dayOfWeek) {
                availabilityRepository.create(new Availability(
                        providerId.intValue(), d, startTime, endTime
                ));
            }
            d = d.plusDays(1);
        }
    }

    public List<Availability> getAvailabilities() {
        return availabilityRepository.findAll();
    }
}
