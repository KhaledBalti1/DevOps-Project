package tn.esprit.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import tn.esprit.spring.entities.Voyageur;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;
import tn.esprit.spring.entities.Voyage;
import tn.esprit.spring.services.IVoyageurService;
import tn.esprit.spring.services.ITrainService;
import tn.esprit.spring.services.IVoyageService;

@RestController
public class RestControllerVoyageur {


    @Autowired
    IVoyageService ivoyageservice;

    @Autowired
    ITrainService itrainservice;

    @Autowired
    IVoyageurService iVoyageurservice;


    @PostMapping("/ajouterVoyage")
    @ResponseBody
    public void ajouterGare(@PathVariable Voyage voiture) {
        ivoyageservice.ajouterVoyage(voiture);
    }


   
    @PostMapping("/ajouterTrain")
    @ResponseBody
    public void ajouterTrain(@PathVariable Train train) {
        itrainservice.ajouterTrain(train);
    }

   
    @PostMapping("/ajouterVoyageur")
    @ResponseBody
    public void ajouterVoyageur(@PathVariable Voyageur Voyageur) {
        iVoyageurservice.ajouterVoyageur(Voyageur);
    }

    
    @PutMapping(value = "/affecterTrainAVoyage/{idtr}/{idvyg}")
    
    public void affecterTrainAVoyage(@PathVariable("idtr") Long idTrain, @PathVariable("idvyg") Long idVoyage) {
        ivoyageservice.affecterTrainAVoyage(idTrain, idVoyage);
    }



    @PutMapping(value = "/affecterTrainAVoyageur/{idc}/{nomgdpt}/{nomgarr}/{heuredept}")
    public void affecterTainAVoyageur(@PathVariable("idc") Long idVoyageur, @PathVariable("nomgdpt") Ville nomGareDepart, @PathVariable("nomgarr") Ville nomGareArrivee, @PathVariable("heuredept") double heureDepart) {
        itrainservice.affecterTainAVoyageur(idVoyageur, nomGareDepart, nomGareArrivee, heureDepart);
    }

    @GetMapping(value = "/TrainPlacesLibres/{nomgdpt}")
    public int trainPlacesLibres(@PathVariable("nomgdpt") Ville nomGareDepart) {
        System.out.println("in controller" + nomGareDepart);
        return itrainservice.trainPlacesLibres(nomGareDepart);
    }

    @GetMapping(value = "/ListerTrainsIndirects/{nomgdpt}/{nomgarr}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Train> listerTrainsIndirects(@PathVariable("nomgdpt") Ville nomGareDepart, @PathVariable("nomgarr") Ville nomGareArrivee) {
        return itrainservice.listerTrainsIndirects(nomGareDepart, nomGareArrivee);
    }

    @PutMapping(value = "/DesaffecterVoyageursTrain/{nomgdpt}/{heuredept}")
    public void desaffecterVoyageursTrain(@PathVariable("nomgdpt") Ville nomGareDepart, @PathVariable("nomgarr") Ville nomGareArrivee, @PathVariable("heuredept") double heureDepart) {
        itrainservice.desaffecterVoyageursTrain(nomGareDepart, nomGareArrivee, heureDepart);
    }

}
