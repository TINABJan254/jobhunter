package vn.hoidanit.jobhunter.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.hoidanit.jobhunter.domain.Subscriber;
import vn.hoidanit.jobhunter.service.SubscriberService;
import vn.hoidanit.jobhunter.util.SecurityUtil;
import vn.hoidanit.jobhunter.util.annotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;


@RestController
@RequestMapping("/api/v1")
public class SubscriberController {

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/subscribers")
    public ResponseEntity<Void> getSubscribers() {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    @PostMapping("/subscribers")
    @ApiMessage("create subscriber")
    public ResponseEntity<Subscriber> addSubscriber(@Valid @RequestBody Subscriber subscriber) throws IdInvalidException {
        if (this.subscriberService.emailExists(subscriber.getEmail())) {
            throw new IdInvalidException("Email already exists");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(subscriberService.save(subscriber));
    }

    @PutMapping("/subscribers")
    @ApiMessage("update subscribers")
    public ResponseEntity<Subscriber> updateSubscriber(@RequestBody Subscriber subscriber) throws IdInvalidException {
        if (this.subscriberService.fetchById(subscriber.getId()).isEmpty()) {
            throw new IdInvalidException("Subscriber does not exist");
        }

        return ResponseEntity.ok(this.subscriberService.update(subscriber));
    }

    @PostMapping("/subscribers/skills")
    @ApiMessage("Get subscriber's skill")
    public ResponseEntity<Subscriber> getSubscribersSkill() throws IdInvalidException {
        String email = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        return ResponseEntity.ok().body(this.subscriberService.findByEmail(email));
    }
}