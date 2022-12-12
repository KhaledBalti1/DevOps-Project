package tn.esprit.spring.services;

import org.springframework.stereotype.Service;
import tn.esprit.spring.entities.Train;
import tn.esprit.spring.entities.Ville;
import tn.esprit.spring.entities.Voyage;
import tn.esprit.spring.repository.TrainRepository;
import tn.esprit.spring.repository.VoyageRepository;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.spring.repository.VoyageurRepository;

import tn.esprit.spring.entities.Voyageur;

import java.util.ArrayList;
import java.util.List;


import org.springframework.transaction.annotation.Transactional;



import org.springframework.scheduling.annotation.Scheduled;

@Service
public class TrainServiceImpl implements ITrainService {


    @Autowired
    VoyageurRepository VoyageurRepository;


    @Autowired
    TrainRepository trainRepository;

    @Autowired
    VoyageRepository voyageRepository;


    public void ajouterTrain(Train t) {

        trainRepository.save(t);
    }

    public int TrainPlacesLibres(Ville nomGareDepart) {
        int cpt = 0;
        int occ = 0;
        List<Voyage> listvoyage = (List<Voyage>) voyageRepository.findAll();
        System.out.println("tailee" + listvoyage.size());

        for (int i = 0; i < listvoyage.size(); i++) {
            System.out.println("gare" + nomGareDepart + "value" + listvoyage.get(0).getGareDepart());
            if (listvoyage.get(i).getGareDepart() == nomGareDepart) {
                cpt = cpt + listvoyage.get(i).getTrain().getNbPlaceLibre();
                occ = occ + 1;
                System.out.println("cpt " + cpt);
            } 
        }
        return cpt / occ;
    }


    public List<Train> listerTrainsIndirects(Ville nomGareDepart, Ville nomGareArrivee) {

        List<Train> lestrainsRes = new ArrayList<>();
        List<Voyage> lesvoyage ;
        lesvoyage = (List<Voyage>) voyageRepository.findAll();
        for (int i = 0; i < lesvoyage.size(); i++) {
            if (lesvoyage.get(i).getGareDepart() == nomGareDepart) {
                for (int j = 0; j < lesvoyage.size(); j++) {
                    if (lesvoyage.get(i).getGareArrivee() == lesvoyage.get(j).getGareDepart() & lesvoyage.get(j).getGareArrivee() == nomGareArrivee) {
                        lestrainsRes.add(lesvoyage.get(i).getTrain());
                        lestrainsRes.add(lesvoyage.get(j).getTrain());

                    } 

                }
            }
        }


        return lestrainsRes;
        
    }


    @Transactional
    public void affecterTainAVoyageur(Long idVoyageur, Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {


        System.out.println("taille test");
        Voyageur c = VoyageurRepository.findById(idVoyageur).get();
        List<Voyage> lesvoyages ;
        lesvoyages = voyageRepository.RechercheVoyage(nomGareDepart, nomGareDepart, heureDepart);
        System.out.println("taille" + lesvoyages.size());
        for (int i = 0; i < lesvoyages.size(); i++) {
            if (lesvoyages.get(i).getTrain().getNbPlaceLibre() != 0) {
                lesvoyages.get(i).getMesVoyageurs().add(c);
                lesvoyages.get(i).getTrain().setNbPlaceLibre(lesvoyages.get(i).getTrain().getNbPlaceLibre() - 1);
            } else
                System.out.print("Pas de place disponible pour " + VoyageurRepository.findById(idVoyageur).get().getNomVoyageur());
            voyageRepository.save(lesvoyages.get(i));
        }
    }

    @Override
    public void desaffecterVoyageursTrain(Ville nomGareDepart, Ville nomGareArrivee, double heureDepart) {
        List<Voyage> lesvoyages ;
        lesvoyages = voyageRepository.RechercheVoyage(nomGareDepart, nomGareArrivee, heureDepart);
        
    }

    @Scheduled(fixedRate = 2000)
   
        

	@Override
	public int trainPlacesLibres(Ville nomGareDepart) {
    	  int cpt = 0;
          int occ = 0;
          List<Voyage> listvoyage = (List<Voyage>) voyageRepository.findAll();
          System.out.println("tailee" + listvoyage.size());

          for (int i = 0; i < listvoyage.size(); i++) {
              System.out.println("gare" + nomGareDepart + "value" + listvoyage.get(0).getGareDepart());
              if (listvoyage.get(i).getGareDepart() == nomGareDepart) {
                  cpt = cpt + listvoyage.get(i).getTrain().getNbPlaceLibre();
                  occ = occ + 1;
                  System.out.println("cpt " + cpt);
              } 
          }
          return cpt / occ;
	}

	
	@Override
	 @Scheduled(fixedRate = 2000)
	public void trainsEnGare() {
		List<Voyage> lesvoyages ;
        lesvoyages = (List<Voyage>) voyageRepository.findAll();
        
		
	}
    }




    
