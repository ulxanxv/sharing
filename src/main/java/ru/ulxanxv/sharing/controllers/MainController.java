package ru.ulxanxv.sharing.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.ulxanxv.sharing.models.Auxiliary;
import ru.ulxanxv.sharing.models.Disk;
import ru.ulxanxv.sharing.services.DiskInfoService;
import ru.ulxanxv.sharing.services.DiskSharingService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class MainController {

    private final DiskInfoService diskInfoService;
    private final DiskSharingService diskSharingService;


    @Autowired
    public MainController(DiskInfoService diskInfoService,
                          DiskSharingService diskSharingService) {
        this.diskInfoService = diskInfoService;
        this.diskSharingService = diskSharingService;
    }

    @GetMapping("/disks/all")
    public ResponseEntity<List<Disk>> disks() {
        return ResponseEntity.ok(diskInfoService.userDisks());
    }

    @GetMapping("/disks/free")
    public ResponseEntity<List<Disk>> freeDisks() {
        return ResponseEntity.ok(diskInfoService.freeDisks());
    }

    @GetMapping("/disks/taken")
    public ResponseEntity<List<Disk>> takenDisks() {
        return ResponseEntity.ok(diskInfoService.takenDisks());
    }

    @GetMapping("/disks/given")
    public ResponseEntity<List<Auxiliary>> givenDisks() {
        return ResponseEntity.ok(diskInfoService.givenDisks());
    }

    @PutMapping("/disk/take/{id}")
    public ResponseEntity<?> takeDisk(@PathVariable("id") Long id) {
        return ResponseEntity.ok(diskSharingService.takeDisk(id));
    }

    @PutMapping("/disk/return/{id}")
    public ResponseEntity<?> returnDisk(@PathVariable("id") Long id) {
        return ResponseEntity.ok(diskSharingService.returnDisk(id));
    }
}
