package vn.hoidanit.jobhunter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.hoidanit.jobhunter.domain.Job;
import vn.hoidanit.jobhunter.domain.Skill;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.domain.response.email.ResEmailJob;
import vn.hoidanit.jobhunter.repository.JobRepository;
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

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private EmailService emailService;

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

    public ResEmailJob convertJobToSendEmail(Job job) {
        ResEmailJob res = new ResEmailJob();
        res.setName(job.getName());
        res.setSalary(job.getSalary());
        res.setCompany(new ResEmailJob.CompanyEmail(job.getCompany().getName()));
        List<Skill> skills = job.getSkills();
        List<ResEmailJob.SkillEmail> s = skills.stream().map(skill -> new ResEmailJob.SkillEmail(skill.getName()))
                .collect(Collectors.toList());
        res.setSkills(s);
        return res;
    }

    public void sendSubscribersEmailJobs() {
        List<Subscriber> listSubs = this.subscriberRepository.findAll();
        if (listSubs != null && listSubs.size() > 0) {
            for (Subscriber sub : listSubs) {
                List<Skill> listSkills = sub.getSkills();
                if (listSkills != null && listSkills.size() > 0) {
                    List<Job> listJobs = this.jobRepository.findBySkillsIn(listSkills);
                    if (listJobs != null && listJobs.size() > 0) {

                        List<ResEmailJob> arr = listJobs.stream().map(
                                job -> this.convertJobToSendEmail(job)).collect(Collectors.toList());

                        this.emailService.sendEmailFromTemplateSync(
                                sub.getEmail(),
                                "Cơ hội việc làm hot đang chờ đón bạn, khám phá ngay",
                                "job",
                                sub.getName(), arr);
                    }
                }
            }
        }
    }

    public Subscriber findByEmail(String email) {
        return this.subscriberRepository.findByEmail(email);
    }

}