package vn.hoidanit.jobhunter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.repository.SkillRepository;
import vn.hoidanit.jobhunter.repository.SubscriberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    @Autowired
    private SkillRepository skillRepository;

    public boolean emailExists(String email) {
        return subscriberRepository.existsByEmail(email);
    }

    public Optional<Subscriber> fetchById(long id) {
        return this.subscriberRepository.findById(id);
    }

    public Subscriber save(Subscriber subscriber) {

        if (subscriber.getSkills() != null) {
            List<Long> skillIds = subscriber.getSkills()
                    .stream()
                    .map(Skill::getId)
                    .toList();

            List<Skill> dbSkills = skillRepository.findByIdIn(skillIds);
            subscriber.setSkills(dbSkills);
        }

        return this.subscriberRepository.save(subscriber);
    }

    public Subscriber update(Subscriber subscriber) {
        Subscriber subsDb = subscriberRepository.findById(subscriber.getId())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));

        if (subscriber.getSkills() != null) {
            List<Long> skillIds = subscriber.getSkills()
                    .stream()
                    .map(Skill::getId)
                    .toList();

            List<Skill> dbSkills = skillRepository.findByIdIn(skillIds);
            subsDb.setSkills(dbSkills);
        }

        return this.subscriberRepository.save(subsDb);
    }

}
