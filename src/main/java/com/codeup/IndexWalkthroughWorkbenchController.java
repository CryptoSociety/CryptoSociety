package com.codeup;

import com.codeup.models.Crypto;
import com.codeup.models.Cryptos;
import com.codeup.models.Walkthrough;
import com.codeup.models.Walkthroughs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class IndexWalkthroughWorkbenchController {

    @Autowired
    Cryptos cryptosRepo;

    @Autowired
    Walkthroughs walkthroughsRepo;

    @GetMapping("")
    public String landingPage(Model model) {
        List<Crypto> mostRecentCryptos = cryptosRepo.findFirst3ByActiveEqualsAndIsApprovedEqualsOrderByCreationDateDesc(true, true);
//        TODO: implement most recent in view, and check if ordering is right or needs to be reversed
        model.addAttribute("mostRecentCryptos", mostRecentCryptos);
        return "index";
    }

    @GetMapping("/walkthroughs")
    public String walkthroughIndex(Model model) {
        List<Walkthrough> allWalkthroughs = walkthroughsRepo.findAllByOrderByDifficultyAsc();
        model.addAttribute("allWalkthroughs", allWalkthroughs);
        return "walkthrough";
    }

//    @GetMapping("/walkthroughs/{scheme}")
//    public String walkthroughPage(@PathVariable String scheme, Model model){
//        List<Walkthrough> allWalkthroughs = walkthroughsRepo.findAllByOrderByDifficultyAsc();
//        Walkthrough current = walkthroughsRepo.findFirstByScheme(scheme);
//        model.addAttribute("allWalkthroughs", allWalkthroughs);
//        model.addAttribute("currentWalkthrough", current);
//        return "individualWalkthrough";
//    }

    @GetMapping("/walkthroughs/caesar")
    public String caesarWalkthrough() {
        return "walkthroughs/caesarWalkthrough";
    }

    @GetMapping("/walkthroughs/railfence")
    public String railfenceWalkthrough() {
        return "walkthroughs/railfenceWalkthrough";
    }

    @GetMapping("/walkthroughs/atbash")
    public String atbashWalkthrough() {
        return "walkthroughs/atbashWalkthrough";
    }

    @GetMapping("/walkthroughs/kamasutra")
    public String kamasutraWalkthrough() {
        return "walkthroughs/kamasutraWalkthrough";
    }

    @GetMapping("/walkthroughs/vigenere")
    public String vigenereWalkthrough() {
        return "walkthroughs/vigenereWalkthrough";
    }

    @GetMapping("/walkthroughs/vigenereDecrypt")
    public String vigenereDecryptWalkthrough() {
        return "walkthroughs/vigeneredecryptWalkthrough";
    }

    @GetMapping("/walkthroughs/cryptanalysis")
    public String cryptanalysisWalkthrough() {
        return "walkthroughs/cryptanalysisWalkthrough";
    }

    @GetMapping("/toolbox")
    public String toolIndex() {
        return "toolindex";
    }

//    @GetMapping("/tool")
//    public String tool() {
//        return "tool";
//    }

}

