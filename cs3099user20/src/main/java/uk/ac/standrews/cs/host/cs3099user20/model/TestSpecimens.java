package uk.ac.standrews.cs.host.cs3099user20.model;


import com.github.therapi.runtimejavadoc.repack.com.eclipsesource.json.Json;
import org.json.JSONObject;

public class TestSpecimens {

    // ------------------------------------------- ARTICLE SPECIMEN --------------------------------------------------//
    public Article testArticle(int i) {

        Article article = null;
        String[] authors;
        Version[] versions;

        switch(i){
            case 1:
                authors = new String[]{testAuthors(0), testAuthors(14), testAuthors(15)};
                versions = new Version[]{testVersions(1)};
                article = new Article(2, "Rooter: A Methodology for the Typical Unification of Access Points and Redundancy", "Recent interest in totally Smale isometries has centered on computing null, to-\n" +
                        "tally Gaussian homeomorphisms. Therefore this reduces the results of [37] to an\n" +
                        "easy exercise. Here, uncountability is obviously a concern.", authors, "MIT", "20:27ad8496-a243-4246-a155-a990a5c25390", 18, versions);
                break;
            case 2:
                authors = new String[]{testAuthors(1)};
                versions = new Version[]{testVersions(2)};
                article = new Article(3, "The Influence of Probabilistic Methodologies on Networking", "Let us suppose we are given an almost everywhere composite, one-to-one, discretely measurable\n" +
                        "modulus J′. B. White’s derivation of algebraically contravariant paths was a milestone in stochastic calculus.\n" +
                        "We show that there exists a bounded subset. The work in [16] did not consider the solvable case. Moreover,\n" +
                        "it would be interesting to apply the techniques of [16] to reversible, contra-everywhere Euclidean, onto matrices", authors, "CC BY", "20:7b4a8fe-722f-42a1-9b9a-b4e67971a5b2", 19, versions);
                break;
            case 3:
                authors = new String[]{testAuthors(2)};
                versions = new Version[]{testVersions(3)};
                article = new Article(4, "Harnessing Byzantine Fault Tolerance Using Classical Theory", "Assume there exists a tangential, semi-partial, Gaussian and Kovalevskaya associative, left-\n" +
                        "countable triangle. Recently, there has been much interest in the classification of smooth cat-\n" +
                        "egories. We show that Ψ < −∞. In [3], the authors address the connectedness of Weierstrass,\n" +
                        "Volterra, trivially φ-singular isomorphisms under the additional assumption that F is closed.\n" +
                        "Recently, there has been much interest in the derivation of Lagrange vectors.", authors, "CC BY", "20:ef9c4bff-1d55-4318-b86e-41813f61320c", 20, versions);
                break;
            case 4:
                authors = new String[]{testAuthors(3)};
                versions = new Version[]{testVersions(4)};
                article = new Article(5, "Synthesizing Checksums and Lambda Calculus using Jog", "A central problem in introductory Euclidean calculus is the derivation of invertible sets. So it is\n" +
                        "essential to consider that k may be canonically smooth.", authors, "CC BY-NC", "20:2c14456d-bb33-4266-a3d8-8168717cc401", 21, versions);
                break;

            case 5:
                authors = new String[]{testAuthors(4)};
                versions = new Version[]{testVersions(5), testVersions(15)};
                article = new Article(6, "On the Study of the Ethernet", "Recently, there has been much interest in the characterization of irreducible morphisms. It was\n" +
                        "Erd ̋os who first asked whether associative arrows can be studied. It was Jacobi who first asked\n" +
                        "whether open systems can be classified", authors, "CC BY-NC ND", "20:01496cee-c8ea-4b07-9688-35c04ab89514", 33, versions);
                break;

            case 6:
                authors = new String[]{testAuthors(5)};
                versions = new Version[]{testVersions(6), testVersions(16)};
                article = new Article(7, "Deconstructing the Location-Identity Split", "Let A be a pseudo-combinatorially Markov, nonnegative function. Recent interest in Gaussian\n" +
                        "Lie spaces has centered on studying globally independent points", authors, "CC BY-ND", "20:518f4fb6-0d90-46e1-873e-7f3ee99400c0", 64, versions);
                break;

            case 7:
                authors = new String[]{testAuthors(6)};
                versions = new Version[]{testVersions(7), testVersions(17)};
                article = new Article(8, "Non-Characteristic Uniqueness for Quasi-Real Manifolds", "Is it possible to derive trivial, anti-Hardy, co-Hermite fields? In future work,\n" +
                        "we plan to address questions of structure as well as uniqueness. Now this could\n" +
                        "shed important light on a conjecture of Galois.", authors, "CTA", "20:476857e1-cb39-441d-9896-ce19d9913dca", 35, versions);
                break;

            case 8:
                authors = new String[]{testAuthors(7)};
                versions = new Version[]{testVersions(8), testVersions(18)};
                article = new Article(9, "On the Continuity of Compactly Real Algebras", "A central problem in universal Galois theory is the construction of admissible, unconditionally\n" +
                        "sub-degenerate isomorphisms. So recently, there has been much interest in the extension of contra-\n" +
                        "maximal functors.", authors, "CTA", "20:f1e84b9b-d50a-46ff-8663-83befbe7c1c0", 36, versions);
                break;
            case 9:
                authors = new String[]{testAuthors(8)};
                versions = new Version[]{testVersions(9), testVersions(19)};
                article = new Article(10, "Existence in Logical Mechanics", "A central problem in linear model theory is the computation of universal\n" +
                        "lines. Next, recent interest in multiplicative, stochastic, globally canoni-\n" +
                        "cal numbers has centered on deriving locally Kepler–G ̈odel domains. A.\n" +
                        "Fourier’s computation of conditionally Artinian, meager, super-trivially in-\n" +
                        "vertible subrings was a milestone in arithmetic", authors, "CTA", "20:d8f9a5fc-2e5c-4604-ab65-d86433cfaf25", 37, versions);

                break;
            case 10:
                authors = new String[]{testAuthors(9)};
                versions = new Version[]{testVersions(10), testVersions(20)};
                article = new Article(11, "ON THE EXISTENCE OF ONE-TO-ONE, QUASI-POINCAR ́E, SYMMETRIC HOMEOMORPHISMS", "Suppose we are given a complex, multiply Siegel scalar j. \\n\"" +
                        " In  the main result was the construction of universal scalars. We show that Lebesgue’s condition is satisfied. It is essential to consider j that d may be trivially singular. So is it possible to\\n\"" +
                        " extend contra-unconditionally meager subalgebras?", authors, "YALE", "20:82b631d3-5308-4dec-9e9d-5abdd0526aff", 38, versions);

                break;
            case 11:
                authors = new String[]{testAuthors(10)};
                versions = new Version[]{testVersions(11), testVersions(21)};
                article = new Article(12, "Symmetric Locality for Functions", "Recently, there has been much interest in the characterization of commutative scalars", authors, "YALE", "20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f", 39, versions);

                break;
            case 12:
                authors = new String[]{testAuthors(11)};
                versions = new Version[]{testVersions(12), testVersions(22)};
                article = new Article(13, "STOCHASTICALLY HUYGENS HOMOMORPHISMS OVER EMPTY, EMPTY, CONTRA-DISCRETELY COMPOSITE FIELDS", "Is it possible to construct tangential, Germain algebras? A. Robinson’s\n" +
                        "extension of functions was a milestone in algebraic knot theory. Now recent\n" +
                        "interest in .....", authors, "HARVARD CAT", "20:2ca70ccd-b1b2-415a-8795-5a0c9bcb1656", 40, versions);

                break;
            case 13:
                authors = new String[]{testAuthors(12)};
                versions = new Version[]{testVersions(13), testVersions(23)};
                article = new Article(14, "KOLMOGOROV–LINDEMANN UNIQUENESS FOR TOPOI", "Recent interest in Cayley functors has centered on deriving contra-unconditionally\n" +
                        "ordered, generic random variables. ", authors, "MIT", "20:fec6b855-53da-45ed-9130-9433ec7c74ea", 41, versions);
                break;
            case 14:
                authors = new String[]{testAuthors(13)};
                versions = new Version[]{testVersions(14)};
                article = new Article(15, "Linearly Sub-Stable Planes of Covariant, Hippocrates Random Variables and Countability", "it is shown that every multiplicative isomorphism is v-simply asso-\n" +
                        "ciative. A central problem in non-linear representation theory is the deriva-\n" +
                        "tion of hyper-multiplicative categories", authors, "OAT", "20:ed2a3b42-c174-4b9f-861d-07e7d1e9074e", 31, versions);


        }

        return article;
    }

    public Article testNewArticle() {
        Version[] versions = new Version[0];
        return new Article(30, "SEXY", "This article is full of wonders...", new String[] {"Sexy", "Beautiful"}, "Sexy, Beautiful", this.testUsers(1).getUserID(), 42, versions, this.testFileData());
    }

    private String testAuthors(int i) {
        String[] author = new String[16];

        author[0] = "Bethany Gardner";
        author[1] = "Liam Greenwood";
        author[2] = "Lucas Brown";
        author[3] = "Liam Cox";
        author[4] = "Denny Murphy";
        author[5] = "Adina Collins";
        author[6] = "Adeline Villiger";
        author[7] = "Carter Saunders";
        author[8] = "Oliver Hunt";
        author[9] = "Holly Upsdell";
        author[10]  = "Chadwick Weatcroft";
        author[11] = "Leilani Martin";
        author[12] = "Maddison Waterhouse";
        author[13] = "Marla Mcgregor";
        author[14] = "Ron Brock";
        author[15] = "Luna Moreno";

        return author[i];
    }

    private Version testVersions(int i) {
        Version version = null;

        switch (i) {
            case 1:
                version = new Version(18, 1,0);
                break;
            case 2:
                version = new Version(19, 0,0);
                break;
            case 3:
                version = new Version(20, 0,1);
                break;
            case 4:
                version = new Version(21, 1,0);
                break;
            case 5:
                version = new Version(22,0,1);
                break;
            case 6:
                version = new Version(23, 1,0);
                break;
            case 7:
                version = new Version(24,0,1);
                break;
            case 8:
                version = new Version(25, 1,0);
                break;
            case 9:
            version = new Version(26, 1,0);
                break;
            case 10:
                version = new Version(27, 1,0);
                break;
            case 11:
                version = new Version(28,0,1);
                break;
            case 12:
                version = new Version(29, 0,1);
                break;
            case 13:
                version = new Version(30,1,0);
                break;
            case 14:
                version = new Version(31, 1,0);
                break;
            case 15:
                version = new Version(33, 0,2);
                break;
            case 16:
                version = new Version(34, 0,2);
                break;
            case 17:
                version = new Version(35, 0,2);
                break;
            case 18:
                version = new Version(36, 2,0);
                break;
            case 19:
                version = new Version(37, 2,0);
                break;
            case 20:
                version = new Version(38, 2,1);
                break;
            case 21:
                version = new Version(39, 0,2);
                break;
            case 22:
                version = new Version(40, 0,2);
                break;
            case 23:
                version = new Version(41, 2,0);
        }

        return version;
    }

//----------------------------------------------- MODERATOR SPECIMEN -------------------------------------------------//
    public String testModerators(int i) {
        String modID = null;
        switch(i) {
            case 1:
                modID = "20:6f048203-7846-4f16-bf9f-7c7ee16163f4";
                break;
            case 2:
                modID = "20:c62c919d-9276-43c5-9f94-d830a73c5516";

        }
        return modID;
    }

    public Login moderatorLogin() {
        return new Login("LailaDale70@extex.org", "test18", "20");
    }


    //--------------------------------------------- REVIEWER LOGIN
    public Login reviewerLogin() {
        return new Login("BP3082@nickia.com", "test5", "20");
    }


    public Reviewer testReviewers(int i) {
        Reviewer reviewer = null;

        switch(i) {
            case 1:
                reviewer = new Reviewer("20:1e786274-ff51-459a-94f7-2f1f62ecf0c4", true, "Brian is an expert on networks, system protocols, and new forms of computer architecture. He is a genius with an IQ of 8 million.","University of St Andrews","Networks");
                break;
            case 2:
                reviewer = new Reviewer("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", true, "Anthony is a master of Crypto. He owns 600 NFTs, including a rare \"Bored Ape.\"", "University of Glasgow", "Blockchain Technology");
                break;
            case 3:
                reviewer = new Reviewer("20:2aefab22-29a1-43b4-84bb-bbee49c988e9", false, "Absolute freak", "McDonalds Nation", "Losing and failing");
                break;
            case 4:
                reviewer = new Reviewer("20:45067c5c-c386-47d8-a381-0822700b9642", true, "Mary once wrote code with O(1) complexity. Rumour has it she has done it twice. ", "University of Aberdeen", "Algorithms");
                break;
            case 5:
                reviewer = new Reviewer("20:6b381604-2f47-486b-b61f-24820bd46398", true, "Anne has been a lead developer of 12 programming languages. Including many joke ones. She's got a plan up her sleeve...", "University of Stirling", "Programming Languages");
                break;
            case 6:
                reviewer = new Reviewer("20:b82cda19-e36b-4f63-82ea-e407b15c510a", false, "Percy fell in love with computers when he was 5 years old. He does 10 jumping jacks every day and then codes for 18 hours straight.", "University of Edinburgh", "Databases");
                break;
            case 7:
                reviewer = new Reviewer("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", true, "Freya is a Figma genius, and also is amazing at GUI development. She likes React, Figma, and some other stuff, too!", "University of Strathclyde", "UX Design");
        }
        return reviewer;
    }


    public Assignment[] testAssignments() throws Exception {
        Assignment[] assignment = new Assignment[30];

        assignment[0] = new Assignment("20:1e786274-ff51-459a-94f7-2f1f62ecf0c4",2,false);
        assignment[1] = new Assignment("20:1e786274-ff51-459a-94f7-2f1f62ecf0c4",5,false);
        assignment[2] = new Assignment("20:1e786274-ff51-459a-94f7-2f1f62ecf0c4",6,false);
        assignment[3] = new Assignment("20:1e786274-ff51-459a-94f7-2f1f62ecf0c4",9,false);
        assignment[4] = new Assignment("20:1e786274-ff51-459a-94f7-2f1f62ecf0c4",10 ,false);
        assignment[5] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",2,false);
        assignment[6] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",6,false);
        assignment[7] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",7,false);
        assignment[8] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",9,false);
        assignment[9] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",10,false);
        assignment[10] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",11,false);
        assignment[11] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",14,false);
        assignment[12] = new Assignment("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc",15,false);
        assignment[13] = new Assignment("20:45067c5c-c386-47d8-a381-0822700b9642",2,false);
        assignment[14]  = new Assignment("20:45067c5c-c386-47d8-a381-0822700b9642",4,false);
        assignment[15] = new Assignment("20:45067c5c-c386-47d8-a381-0822700b9642",5,false);
        assignment[16] = new Assignment("20:45067c5c-c386-47d8-a381-0822700b9642",7,false);
        assignment[17] = new Assignment("20:45067c5c-c386-47d8-a381-0822700b9642",11,false);
        assignment[18] = new Assignment("20:45067c5c-c386-47d8-a381-0822700b9642",14,false);
        assignment[19] = new Assignment("20:6b381604-2f47-486b-b61f-24820bd46398",4,false);
        assignment[20] = new Assignment("20:6b381604-2f47-486b-b61f-24820bd46398",15,false);
        assignment[21] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",4,false);
        assignment[22] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",5,false);
        assignment[23] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",6,false);
        assignment[24] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",7,false);
        assignment[25] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",9,false);
        assignment[26] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",10,false);
        assignment[27] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",11,false);
        assignment[28] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",14,false);
        assignment[29] = new Assignment("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd",15,false);

        return assignment;
        }

        public Pagination testPagination() {
        return new Pagination("20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f", 4, 1, 16);
    }

        public User testUsers(int i) {

            User user = null;
            switch (i) {
                case 1:
                    user = new User("20:476857e1-cb39-441d-9896-ce19d9913dca","Adeline_Villiger3806@grannar.com");
                    break;
                case 2:
                    user = new User("20:518f4fb6-0d90-46e1-873e-7f3ee99400c0","Adina_Collins7787@nickia.com");
                    break;
                case 3:
                    user = new User("20:c62c919d-9276-43c5-9f94-d830a73c5516","Aisha_Needham4441@bauros.biz");
                    break;
                case 4:
                    user = new User("20:2aefab22-29a1-43b4-84bb-bbee49c988e9","Alba_Phillips2874@gembat.biz");
                    break;
                case 5:
                    user = new User("20:0f264d7b-2060-4e6e-adea-3bc4ad6dd798","Allison_Clarke4851@gompie.com");
                    break;
                case 6:
                    user = new User("20:6b381604-2f47-486b-b61f-24820bd46398","AnneW150@bungar.biz");
                    break;
                case 7:
                    user = new User("20:29f472f2-bcfd-4564-bd7a-92e50983ccbc","Anthony_Emerson3338@ovock.tech");
                    break;
                case 8:
                    user = new User("20:1e786274-ff51-459a-94f7-2f1f62ecf0c4","BP3082@nickia.com");
                    break;
                case 9:
                    user = new User("20:45113161-8c86-4181-b8df-d83828a72856","BarneyThorpe1317@guentu.biz");
                    break;
                case 10:
                    user = new User("20:27ad8496-a243-4246-a155-a990a5c25390","Bethany_Gardner2804@deavo.com");
                    break;
                case 11:
                    user = new User("20:f1e84b9b-d50a-46ff-8663-83befbe7c1c0","Carter_Saunders6392@qater.org");
                    break;
                case 12:
                    user = new User("20:a2f5689e-a5ae-4a9c-a7fd-6ab5b86ffd0f","Chadwick_Weatcroft5348@grannar.com");
                    break;
                case 13:
                    user = new User("20:de351720-c2b7-434b-8f9e-31981b869185","Clint_Newman8928@bulaffy.com");
                    break;
                case 14:
                    user = new User("20:d264cc0f-1f48-446d-b458-ea39e429e30d","DanielGates2698@jiman.org");
                    break;
                case 15:
                    user = new User("20:01496cee-c8ea-4b07-9688-35c04ab89514","Denny_Murphy2566@nickia.com");
                    break;
                case 16:
                    user = new User("20:2450f138-ed08-420c-bf17-61f40ebbfa18","Elle_Hewitt@mafthy.com");
                    break;
                case 17:
                    user = new User("20:d5e9e911-a1fd-4620-b3db-12e7f76521bd","Freya_Evans7700@nimogy.biz");
                    break;
                case 18:
                    user = new User("20:15b88d9a-68e1-4b6a-9c98-954692f38222","Hank_Walsh1455@sheye.org");
                    break;
                case 19:
                    user = new User("20:b82cda19-e36b-4f63-82ea-e407b15c510a","HendersonPercy3167@joiniaa.com");
                    break;
                case 20:
                    user = new User("20:82b631d3-5308-4dec-9e9d-5abdd0526aff","Holly_Upsdell3646@tonsy.org");
                    break;
                case 21:
                    user = new User("20:cb8e8897-14c1-4ae8-b247-9069be89cd56","Jane_Healy5499@liret.org");
                    break;
                case 22:
                    user = new User("20:38fbd577-d042-4f05-945a-94fbb0f66b02","John_Milner2587@yahoo.com");
                    break;
                case 23:
                    user = new User("20:6f048203-7846-4f16-bf9f-7c7ee16163f4","LailaDale70@extex.org");
                    break;
                case 24:
                    user = new User("20:2ca70ccd-b1b2-415a-8795-5a0c9bcb1656","Leilani_Martin1175@naiker.biz");
                    break;
                case 25:
                    user = new User("20:2c14456d-bb33-4266-a3d8-8168717cc401","Liam_Cox2226@twipet.com");
                    break;
                case 26:
                    user = new User("20:7b4a8fe-722f-42a1-9b9a-b4e67971a5b2","Liam_Greenwood8096@yahoo.com");
                    break;
                case 27:
                    user = new User("20:ef9c4bff-1d55-4318-b86e-41813f61320c","Lucas_Brown6340@iatim.tech");
                    break;
                case 28:
                    user = new User("20:fec6b855-53da-45ed-9130-9433ec7c74ea","Maddison_Waterhouse4817@atink.com");
                    break;
                case 29:
                    user = new User("20:ed2a3b42-c174-4b9f-861d-07e7d1e9074e","Marla_Mcgregor9635@bauros.biz");
                    break;
                case 30:
                    user = new User("20:45067c5c-c386-47d8-a381-0822700b9642","MaryWhinter1419@corti.com");
                    break;
                case 31:
                    user = new User("20:d8f9a5fc-2e5c-4604-ab65-d86433cfaf25","Oliver_Hunt9534@nickia.com");
                    break;
                case 32:
                    user = new User("20:6fa8005d-37d9-42eb-a882-952977d2f71b","Percy_Russell70@ovock.tech");
            }

            return user;

        }

        public DiscussionMessage testDiscussionMessages(int i) {
            DiscussionMessage discussionMessage = null;

            switch (i) {
                case 1:
                    discussionMessage = new DiscussionMessage(1,0,18,"20:cb8e8897-14c1-4ae8-b247-9069be89cd56", "I don't think the 'flask run' command is the one which causes the error here.", "13:41:19");
                    break;
                case 2:
                    discussionMessage = new DiscussionMessage(2,0,18,"20:0f264d7b-2060-4e6e-adea-3bc4ad6dd798", "The reason this creates problems - Visual Studio Code creates a Powershell environment for your command line.", "13:41:19");
                    break;
                case 3:
                    discussionMessage = new DiscussionMessage(3,0,18,"20:38fbd577-d042-4f05-945a-94fbb0f66b02", "Ensure that your Git repository includes composer.json (package settings) and composer.lock (optional, but recommended by Composer to ensure 100% version match across all servers) files\"", "13:41:19");
                    break;
                case 4:
                    discussionMessage = new DiscussionMessage(4,3,18, "20:de351720-c2b7-434b-8f9e-31981b869185","Ensure that your Git repository includes composer.json (package settings) and composer.lock ", "13:41:19");
                    break;
            }
            return discussionMessage;

        }

        public DiscussionMessage[] getDiscussionMessages() {
        DiscussionMessage[] messages = new DiscussionMessage[4];
        for (int i = 1; i < 5; i++) {
            messages[i-1] = this.testDiscussionMessages(i);
        }
        return messages;
    }

        public DiscussionMessage testNewDiscussionMessage() {
        return new DiscussionMessage(5,3,18, "20:de351720-c2b7-434b-8f9e-31981b869185","Party rock anthem in the nation etc ", "2022-03-23 20:23:14");
        }

        public JSONObject testDownload() {
        JSONObject download = new JSONObject();
        download.put("name", "Rooter: A Methodology for the Typical Unification of Access Points and Redundancy");
        JSONObject metadata = new JSONObject();
        metadata.put("license", "MIT");
        metadata.put("categories", "[\"Group 20\"]");
        metadata.put("authors", "[{\"journal\":\"https://cs3099user20.host.cs.st-andrews.ac.uk\",\"userId\":\"20:27ad8496-a243-4246-a155-a990a5c25390\"}]");
        metadata.put("abstract", "Recent interest in totally Smale isometries has centered on computing null, to-\n" +
                "tally Gaussian homeomorphisms. Therefore this reduces the results of [37] to an\n" +
                "easy exercise. Here, uncountability is obviously a concern.");
        download.put("codeVersions", ":[{\"description\":\"\",\"timestamp\":\"2022-03-31T14:14:13Z\",\"files\":[{\"filename\":\"Animal.java\",\"base64Value\":\"cHVibGljIGNsYXNzIEFuaW1hbCB7CiAgICBwdWJsaWMgQW5pbWFsKCkgeyB9CgogICAgcHVibGljIHZvaWQgZWF0KCkgewogICAgICAgIFN5c3RlbS5vdXQucHJpbnRsbigiSSBhbSBhbiBhbmltYWwgZWF0aW5nIik7CiAgICB9Cn0K\",\"mime\":\"text/plain\",\"isDir\":false},{\"filename\":\"BlueSquare.java\",\"base64Value\":\"aW1wb3J0IGphdmEuYXd0Lio7CmltcG9ydCBqYXZheC5zd2luZy4qOwoKcHVibGljIGNsYXNzIEJsdWVTcXVhcmUgewoKICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYWluKFN0cmluZ1tdIGFyZ3MpIHsKICAgICAgICBKRnJhbWUgZnJhbWUgPSBuZXcgSkZyYW1lKCk7CiAgICAgICAgZnJhbWUuc2V0RGVmYXVsdENsb3NlT3BlcmF0aW9uKEpGcmFtZS5FWElUX09OX0NMT1NFKTsKICAgICAgICBmcmFtZS5zZXRTaXplKG5ldyBEaW1lbnNpb24oNTAwLCA1MDApKTsKCiAgICAgICAgR2FtZVBhbmVsIGdhbWVQYW5lbCA9IG5ldyBHYW1lUGFuZWwoKTsKICAgICAgICBmcmFtZS5hZGQoZ2FtZVBhbmVsKTsKCiAgICAgICAgZnJhbWUuc2V0VmlzaWJsZSh0cnVlKTsKICAgIH0KCiAgICBwcml2YXRlIHN0YXRpYyBjbGFzcyBHYW1lUGFuZWwgZXh0ZW5kcyBKUGFuZWwgewoKICAgICAgICBAT3ZlcnJpZGUKICAgICAgICBwdWJsaWMgdm9pZCBwYWludENvbXBvbmVudChHcmFwaGljcyBnKSB7CiAgICAgICAgICAgIHN1cGVyLnBhaW50Q29tcG9uZW50KGcpOwogICAgICAgICAgICBnLnNldENvbG9yKENvbG9yLmJsdWUpOwogICAgICAgICAgICBnLmZpbGxSZWN0KDEwMCwgMjAwLCA1MCwgNTApOwogICAgICAgIH0KCiAgICB9Cgp9Cg==\",\"mime\":\"text/plain\",\"isDir\":false},{\"filename\":\"ArrayExample.java\",\"base64Value\":\"LyoKICogQ3JlYXRlZCBvbiBPY3QgOSwgMjAwNSBhdCA0OjA0OjIxIFBNLgogKi8KCi8qKgogKiBAYXV0aG9yIGpvbmwKICogRXhhbXBsZSBpbGx1c3RyYXRpbmcgcGFzc2luZyBhIHJlZmVyZW5jZSB2YWx1ZSB0byBmaWxsIHVwIGFuIGFycmF5LgogKi8KcHVibGljIGNsYXNzIEFycmF5RXhhbXBsZSB7CgoJcHVibGljIHZvaWQgcHJpbnRBcnJheShpbnRbXSBhcnJheSwgU3RyaW5nIGlkZW50aWZpZXIpIHsKCQlmb3IgKGludCBpID0gMDsgaSA8IGFycmF5Lmxlbmd0aDsgaSsrKQoJCQlTeXN0ZW0ub3V0LnByaW50bG4oaWRlbnRpZmllciArICJbIiArIGkgKyAiXSA9ICIgKyBhcnJheVtpXSk7Cgl9CgoJcHVibGljIHZvaWQgZmlsbEludEFycmF5KGludFtdIGFycmF5KSB7CgkJZm9yIChpbnQgaSA9IDA7IGkgPCBhcnJheS5sZW5ndGg7IGkrKykKCQkJYXJyYXlbaV0gPSBpOwoJfQoKCXB1YmxpYyB2b2lkIHJ1bigpIHsKCQlpbnRbXSBteV9pbnRzID0gbmV3IGludFsxMF07CgkJZmlsbEludEFycmF5KG15X2ludHMpOwoJCXByaW50QXJyYXkobXlfaW50cywgIm15X2ludHMiKTsKCX0KfQo=\",\"mime\":\"text/plain\",\"isDir\":false},{\"filename\":\"TEST/TEST1/TEST3/TEST4/BlueSquare.java\",\"base64Value\":\"aW1wb3J0IGphdmEuYXd0Lio7CmltcG9ydCBqYXZheC5zd2luZy4qOwoKcHVibGljIGNsYXNzIEJsdWVTcXVhcmUgewoKICAgIHB1YmxpYyBzdGF0aWMgdm9pZCBtYWluKFN0cmluZ1tdIGFyZ3MpIHsKICAgICAgICBKRnJhbWUgZnJhbWUgPSBuZXcgSkZyYW1lKCk7CiAgICAgICAgZnJhbWUuc2V0RGVmYXVsdENsb3NlT3BlcmF0aW9uKEpGcmFtZS5FWElUX09OX0NMT1NFKTsKICAgICAgICBmcmFtZS5zZXRTaXplKG5ldyBEaW1lbnNpb24oNTAwLCA1MDApKTsKCiAgICAgICAgR2FtZVBhbmVsIGdhbWVQYW5lbCA9IG5ldyBHYW1lUGFuZWwoKTsKICAgICAgICBmcmFtZS5hZGQoZ2FtZVBhbmVsKTsKCiAgICAgICAgZnJhbWUuc2V0VmlzaWJsZSh0cnVlKTsKICAgIH0KCiAgICBwcml2YXRlIHN0YXRpYyBjbGFzcyBHYW1lUGFuZWwgZXh0ZW5kcyBKUGFuZWwgewoKICAgICAgICBAT3ZlcnJpZGUKICAgICAgICBwdWJsaWMgdm9pZCBwYWludENvbXBvbmVudChHcmFwaGljcyBnKSB7CiAgICAgICAgICAgIHN1cGVyLnBhaW50Q29tcG9uZW50KGcpOwogICAgICAgICAgICBnLnNldENvbG9yKENvbG9yLmJsdWUpOwogICAgICAgICAgICBnLmZpbGxSZWN0KDEwMCwgMjAwLCA1MCwgNTApOwogICAgICAgIH0KCiAgICB9Cgp9Cg==\",\"mime\":\"text/plain\",\"isDir\":false}]}]");
        download.put("metadata", metadata);
        return download;
        }

        public Comment comment(int i) {
            Comment comment = null;
            switch (i) {
                case 1:
                    comment = new Comment( 1, 18, "Animal.java", "20:1e786274-ff51-459a-94f7-2f1f62ecf0c4", "This colours blew my mind.", 2, 3, "2022-03-24 02:23:47" );
                    break;
                case 2:
                    comment = new Comment( 2, 18, "Animal.java", "20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", "This is new school.", 3, 4, "2022-03-24 03:05:51" );
                    break;
                case 3:
                    comment = new Comment( 3, 18, "Animal.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "Good!! Love the use of playfulness and layout!", 6, 7, "2022-03-24 03:08:37" );
                    break;
                case 4:
                    comment = new Comment( 4, 18, "ArrayExample.java", "20:1e786274-ff51-459a-94f7-2f1f62ecf0c4", "Super thought out! Contrast.", 5, 7, "2022-03-24 04:40:19" );
                    break;
                case 5:
                    comment = new Comment( 5, 18, "ArrayExample.java", "20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", "Just sleek!", 20, 21, "2022-03-24 04:40:19" );
                    break;
                case 6:
                    comment = new Comment( 6, 18, "ArrayExample.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "That's sleek and simple ", 16, 19, "2022-03-24 04:40:19" );
                    break;
                case 7:
                    comment = new Comment( 7, 18, "BlueSquare.java", "20:1e786274-ff51-459a-94f7-2f1f62ecf0c4", "It's magical not just sleek!", 4, 8, "2022-03-24 04:40:19" );
                    break;
                case 8:
                    comment = new Comment( 8, 18, "BlueSquare.java", "20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", "Nice spaces", 3, 4, "2022-03-24 04:40:19" );
                    break;
                case 9:
                    comment = new Comment( 9, 18, "BlueSquare.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "Super elegant design", 24, 25, "2022-03-24 04:40:19" );
                    break;
                case 10:
                    comment = new Comment( 10, 21, "ExampleMenusNamed.java", "20:1e786274-ff51-459a-94f7-2f1f62ecf0c4", "This boldness blew my mind.", 3, 5, "2022-03-24 04:40:19" );
                    break;
                case 11:
                    comment = new Comment( 11, 21, "ExampleMenusNamed.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "Amazing work you have here.", 23, 25, "2022-03-24 04:40:19" );
                    break;
                case 12:
                    comment = new Comment( 12, 21, "ExampleMenusNamed.java", "20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", "Really thought out! I'd love to see a video of how it works.", 42, 45, "2022-03-24 04:40:19" );
                    break;
                case 13:
                    comment = new Comment( 13, 21, "tea/ExampleNiceGUI.java", "20:1e786274-ff51-459a-94f7-2f1f62ecf0c4", "Mission accomplished. It's slick mate", 2, 3, "2022-03-24 04:40:19" );
                    break;
                case 14:
                    comment = new Comment( 14, 21, "tea/ExampleNiceGUI.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "I approve your idea ", 12, 13, "2022-03-24 04:40:19" );
                    break;
                case 15:
                    comment = new Comment( 15, 21, "tea/ExampleNiceGUI.java", "20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", "Really simple.", 28, 32, "2022-03-24 04:40:19" );
                    break;
                case 16:
                    comment = new Comment( 16, 21, "tea/Executive.java", "20:1e786274-ff51-459a-94f7-2f1f62ecf0c4", "This is admirable work!", 5, 6, "2022-03-24 04:40:19" );
                    break;
                case 17:
                    comment = new Comment( 17, 21, "tea/Executive.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "Simple. So radiant.", 12, 14, "2022-03-24 04:40:19" );
                    break;
                case 18:
                    comment = new Comment( 18, 21, "tea/Executive.java", "20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", "Truly appealing :", 9, 10, "2022-03-24 04:40:19" );
                    break;
                case 19:
                    comment = new Comment( 19, 23, "Module.java", "20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", "Please explain further", 32, 33, "2022-03-24 04:40:19" );
                    break;
                case 20:
                    comment = new Comment( 20, 23, "Module.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "Clarification", 18, 22, "2022-03-24 04:40:19" );
                    break;
                case 21:
                    comment = new Comment( 21, 23, "Module.java", "20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", "Not an apache method", 9, 10, "2022-03-24 04:40:19" );
                    break;
                case 22:
                    comment = new Comment( 22, 23, "NoughtsAndCrosses.java", "20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", "Requires further documentation", 89, 93, "2022-03-24 04:40:19" );
                    break;
                case 23:
                    comment = new Comment( 23, 23, "NoughtsAndCrosses.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "See previous comments", 104, 112, "2022-03-24 04:40:19" );
                    break;
                case 24:
                    comment = new Comment( 24, 23, "NoughtsAndCrosses.java", "20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", "Are you serious?", 205, 206, "2022-03-24 04:40:19" );
                    break;
                case 25:
                    comment = new Comment( 25, 23, "Person.java", "20:29f472f2-bcfd-4564-bd7a-92e50983ccbc", "Inefficient", 19, 22, "2022-03-24 04:40:19" );
                    break;
                case 26:
                    comment = new Comment( 26, 23, "Person.java", "20:45067c5c-c386-47d8-a381-0822700b9642", "Interesting use", 1, 4, "2022-03-24 04:40:19" );
                    break;
                case 27:
                    comment = new Comment( 27, 23, "Person.java", "20:d5e9e911-a1fd-4620-b3db-12e7f76521bd", "If this works, huge ramifications", 6, 9, "2022-03-24 04:40:19" );
            }
            return comment;

        }


    public Comment editComment() {
        Comment editedComment = this.comment(3);
        editedComment.setText("This is a test to check that comments can be edited!");
        editedComment.setLineStart(4);
        editedComment.setLineEnd(12);
        return editedComment;
    }

    public Comment editCommentInvalid() {
        Comment editedComment = this.comment(3);
        editedComment.setFilename("INVALIDFILE");
        editedComment.setText("This is a test to check that comments can be edited!");
        editedComment.setLineStart(4);
        editedComment.setLineEnd(12);
        return editedComment;
    }

        public SimpleArticle articleEdit() {
        String description = "This is code that will teach you to live.";
        String name = "The joy of the world";
        String license = "COOL";
        String authors = "Beauty, Grace, Joy";
        return new SimpleArticle(description, name, license, authors);
        }

        public Comment newComment() {
            return new Comment(28, 18, "Animal.java", "20:6f048203-7846-4f16-bf9f-7c7ee16163f4", "This is a comment created as a test!", 5, 12, "2022-03-24 04:40:19");
        }

        public Comment newCommentInvalid() {
            return new Comment(28, 18, "Animal.java", "20:6f048203-7846-4dsfdsf6364agf16-bf9f-7c7ee16163f4", "This is a comment created as a test!", 5, 12, "2022-03-24 04:40:19");
        }


        public SimpleFile testFile() {
            return new SimpleFile("22/temp22.txt", false);
        }

        public Login testLogin() {
            return new Login("Chadwick_Weatcroft5348@grannar.com", "test1", "20");
        }

        public Login testNewLogin() {
            return new Login("newUser@user.com", "newPassword", "20");
        }
        public User testFakeUser() {
            return new User("Aisha_Morrison7043@sheye.org", "n4bQgYhMfWWaL+qgxVrQFaO/TxsrC4Is0V1sFbDwCgg=");
        }

        public User testNullUser() {
            return new User(null, null);
        }

        public Reviewer testReviewerNotExists(boolean i) {
            return new Reviewer("0", i, "Unknown Bio", "Unknown Institution", "Unknown Expertise");
        }

    public FileData testFileData(){
        String base64 = "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";
        String mime = "application/zip";
        String filename = "testfile";
        boolean isDir = false;

        return new FileData(filename, isDir, base64, mime);
    }

    public FileData testFileDataInvalid(){
        String base64 = "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";
        String mime = "application/python";
        String filename = "testfile";
        boolean isDir = false;

        return new FileData(filename, isDir, base64, mime);
    }
    public Author[] testAuthors() {
        return new Author[]{
                new Author(testUsers(1).getUserID())
        };
    }

    /*
    public Submission testSubmission() {
        Article testArticle = testArticle(1);
        Version[] versions = testArticle.getVersions();
        return new Submission("Submission test",
                new MetaData(versions[0].getTime(), testArticle.getDescription(), testArticle.getLicense(),
                        new String[0], testAuthors()),
                new CodeVersion[]{new CodeVersion(versions[0].getTime(), new FileData[]{testFileData()})},
                new SuperGroupComment[]{new SuperGroupComment(testArticle.getId())});
    }

     */

    public Search testSearch(String subString) {
        return new Search(subString, new String[]{"20"});
    }

    public Reviewer testNewReviewers() {
        Reviewer reviewer = new Reviewer("20:ef9c4bff-1d55-4318-b86e-41813f61320c", false, "Lucas has several years of networking experience", "University of Wyoming", "Networking");
        return reviewer;
    }

    public Login testNewReviewerLogin() {
        return new Login("Lucas_Brown6340@iatim.tech ", "test30", "20");
    }

    public Article testInvalidArticle() {
        Version[] versions = new Version[0];
        return new Article(31, "SEXY", "This article is full of wonders...", new String[] {}, "", this.testUsers(1).getUserID(), 42, versions, this.testFileData());
    }

    public ReviewerDecision testRejectReviewerDecision() {
        return new ReviewerDecision("20:6f048203-7846-4f16-bf9f-7c7ee16163f4", "20:2aefab22-29a1-43b4-84bb-bbee49c988e9");
    }

    public ReviewerDecision testAcceptReviewerDecision() {
        return new ReviewerDecision("20:6f048203-7846-4f16-bf9f-7c7ee16163f4", "20:b82cda19-e36b-4f63-82ea-e407b15c510a");
    }
}

    

