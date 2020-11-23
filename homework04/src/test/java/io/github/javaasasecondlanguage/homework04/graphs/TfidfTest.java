package io.github.javaasasecondlanguage.homework04.graphs;

import io.github.javaasasecondlanguage.homework04.GraphPartBuilder;
import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.nodes.ProcNode;
import io.github.javaasasecondlanguage.homework04.ops.mappers.RetainColumnsMapper;
import io.github.javaasasecondlanguage.homework04.utils.ListDumper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.List;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TfidfTest {

    private ProcNode inputNode;
    private ProcNode outputNode;

    @BeforeEach
    void setUp() {
        var graph = Tfidf.createGraph();

        assertNotNull(graph.getInputNodes());
        assertNotNull(graph.getInputNodes());

        assertEquals(1, graph.getInputNodes().size());
        assertEquals(1, graph.getOutputNodes().size());

        inputNode = graph.getInputNodes().get(0);
        outputNode = graph.getOutputNodes().get(0);

    }

    @Test
    void general() {
        var listDumper = new ListDumper();
        GraphPartBuilder
                .startFrom(outputNode)
                .sortBy(List.of("Id", "Word"))
                .map(new RetainColumnsMapper(List.of("Id", "Word")))
                .map(listDumper);

        for (var record : inputRecords) {
            inputNode.push(record, 0);
        }
        inputNode.push(Record.terminalRecord(), 0);

        List<Record> actualRecords = listDumper.getRecords();

        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{"Id", "Author", "Text"},
            new Object[][]{
                    {1, "Garrus", "I'm Garrus Vakarian and this is now my favorite spot on the Citadel."},
                    {2, "Illusive", "Strength for Cerberus is strength for every human. Cerberus is humanity."},
                    {3, "Tali", "After time adrift among open stars, along tides of light and through shoals of dust, I will return to where I began."},
                    {4, "Miranda", "I settle for nothing but the best."},
                    {5, "Legion", "Geth do not intentionally infiltrate."},
                    {6, "Illusive", "You're unique. Not just in terms of what you've accomplished, but what you represent."},
                    {7, "Garrus", "Can it wait for a bit? I'm in the middle of some calibrations."},
                    {8, "Legion", "Does this unit have a soul?"},
                    {9, "Mordin", "Lots of ways to help people. Sometimes heal patients; sometimes execute dangerous people. Either way helps."},
                    {10, "Miranda", "We're all asking you to do the impossible, Shepard. No pressure."},
                    {11, "Garrus", "I had reach, but she had flexibility."},
                    {12, "Garrus", "You know me, always like to savor that last shot before popping the heat sink."},
                    {13, "Legion", "Does this unit have a soul?"},
                    {14, "Mordin", "No glands, replaced by tech. No digestive system, replaced by tech. No soul. Replaced by tech."},
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"Id", "Word"},
            new Object[][]{
                    {1, "and"},             // {1, "and", 0.254}
                    {1, "citadel"},         // {1, "citadel", 0.293}
                    {1, "favorite"},        // {1, "favorite", 0.293}
                    {1, "garrus"},          // {1, "garrus", 0.293}
                    {1, "i"},               // {1, "i", 0.186}
                    {1, "is"},              // {1, "is", 0.254}
                    {1, "m"},               // {1, "m", 0.254}
                    {1, "my"},              // {1, "my", 0.293}
                    {1, "now"},             // {1, "now", 0.293}
                    {1, "on"},              // {1, "on", 0.293}
                    {1, "spot"},            // {1, "spot", 0.293}
                    {1, "the"},             // {1, "the", 0.186}
                    {1, "this"},            // {1, "this", 0.226}
                    {1, "vakarian"},        // {1, "vakarian", 0.293}
                    {2, "cerberus"},        // {2, "cerberus", 0.494}
                    {2, "every"},           // {2, "every", 0.247}
                    {2, "for"},             // {2, "for", 0.381}
                    {2, "human"},           // {2, "human", 0.247}
                    {2, "humanity"},        // {2, "humanity", 0.247}
                    {2, "is"},              // {2, "is", 0.428}
                    {2, "strength"},        // {2, "strength", 0.494}
                    {3, "adrift"},          // {3, "adrift", 0.219}
                    {3, "after"},           // {3, "after", 0.219}
                    {3, "along"},           // {3, "along", 0.219}
                    {3, "among"},           // {3, "among", 0.219}
                    {3, "and"},             // {3, "and", 0.19}
                    {3, "began"},           // {3, "began", 0.219}
                    {3, "dust"},            // {3, "dust", 0.219}
                    {3, "i"},               // {3, "i", 0.279}
                    {3, "light"},           // {3, "light", 0.219}
                    {3, "of"},              // {3, "of", 0.305}
                    {3, "open"},            // {3, "open", 0.219}
                    {3, "return"},          // {3, "return", 0.219}
                    {3, "shoals"},          // {3, "shoals", 0.219}
                    {3, "stars"},           // {3, "stars", 0.219}
                    {3, "through"},         // {3, "through", 0.219}
                    {3, "tides"},           // {3, "tides", 0.219}
                    {3, "time"},            // {3, "time", 0.219}
                    {3, "to"},              // {3, "to", 0.153}
                    {3, "where"},           // {3, "where", 0.219}
                    {3, "will"},            // {3, "will", 0.219}
                    {4, "best"},            // {4, "best", 0.447}
                    {4, "but"},             // {4, "but", 0.345}
                    {4, "for"},             // {4, "for", 0.345}
                    {4, "i"},               // {4, "i", 0.284}
                    {4, "nothing"},         // {4, "nothing", 0.447}
                    {4, "settle"},          // {4, "settle", 0.447}
                    {4, "the"},             // {4, "the", 0.284}
                    {5, "do"},              // {5, "do", 0.408}
                    {5, "geth"},            // {5, "geth", 0.471}
                    {5, "infiltrate"},      // {5, "infiltrate", 0.471}
                    {5, "intentionally"},   // {5, "intentionally", 0.471}
                    {5, "not"},             // {5, "not", 0.408}
                    {6, "accomplished"},    // {6, "accomplished", 0.231}
                    {6, "but"},             // {6, "but", 0.178}
                    {6, "in"},              // {6, "in", 0.2}
                    {6, "just"},            // {6, "just", 0.231}
                    {6, "not"},             // {6, "not", 0.2}
                    {6, "of"},              // {6, "of", 0.161}
                    {6, "re"},              // {6, "re", 0.2}
                    {6, "represent"},       // {6, "represent", 0.231}
                    {6, "terms"},           // {6, "terms", 0.231}
                    {6, "unique"},          // {6, "unique", 0.231}
                    {6, "ve"},              // {6, "ve", 0.231}
                    {6, "what"},            // {6, "what", 0.463}
                    {6, "you"},             // {6, "you", 0.535}
                    {7, "a"},               // {7, "a", 0.232}
                    {7, "bit"},             // {7, "bit", 0.302}
                    {7, "calibrations"},    // {7, "calibrations", 0.302}
                    {7, "can"},             // {7, "can", 0.302}
                    {7, "for"},             // {7, "for", 0.232}
                    {7, "i"},               // {7, "i", 0.192}
                    {7, "in"},              // {7, "in", 0.261}
                    {7, "it"},              // {7, "it", 0.302}
                    {7, "m"},               // {7, "m", 0.261}
                    {7, "middle"},          // {7, "middle", 0.302}
                    {7, "of"},              // {7, "of", 0.21}
                    {7, "some"},            // {7, "some", 0.302}
                    {7, "the"},             // {7, "the", 0.192}
                    {7, "wait"},            // {7, "wait", 0.302}
                    {8, "a"},               // {8, "a", 0.384}
                    {8, "does"},            // {8, "does", 0.431}
                    {8, "have"},            // {8, "have", 0.431}
                    {8, "soul"},            // {8, "soul", 0.384}
                    {8, "this"},            // {8, "this", 0.384}
                    {8, "unit"},            // {8, "unit", 0.431}
                    {9, "dangerous"},       // {9, "dangerous", 0.23}
                    {9, "either"},          // {9, "either", 0.23}
                    {9, "execute"},         // {9, "execute", 0.23}
                    {9, "heal"},            // {9, "heal", 0.23}
                    {9, "help"},            // {9, "help", 0.23}
                    {9, "helps"},           // {9, "helps", 0.23}
                    {9, "lots"},            // {9, "lots", 0.23}
                    {9, "of"},              // {9, "of", 0.16}
                    {9, "patients"},        // {9, "patients", 0.23}
                    {9, "people"},          // {9, "people", 0.459}
                    {9, "sometimes"},       // {9, "sometimes", 0.459}
                    {9, "to"},              // {9, "to", 0.16}
                    {9, "way"},             // {9, "way", 0.23}
                    {9, "ways"},            // {9, "ways", 0.23}
                    {10, "all"},             // {10, "all", 0.321}
                    {10, "asking"},          // {10, "asking", 0.321}
                    {10, "do"},              // {10, "do", 0.277}
                    {10, "impossible"},      // {10, "impossible", 0.321}
                    {10, "no"},              // {10, "no", 0.277}
                    {10, "pressure"},        // {10, "pressure", 0.321}
                    {10, "re"},              // {10, "re", 0.277}
                    {10, "shepard"},         // {10, "shepard", 0.321}
                    {10, "the"},             // {10, "the", 0.204}
                    {10, "to"},              // {10, "to", 0.223}
                    {10, "we"},              // {10, "we", 0.321}
                    {10, "you"},             // {10, "you", 0.247}
                    {11, "but"},             // {11, "but", 0.272}
                    {11, "flexibility"},     // {11, "flexibility", 0.354}
                    {11, "had"},             // {11, "had", 0.707}
                    {11, "i"},               // {11, "i", 0.225}
                    {11, "reach"},           // {11, "reach", 0.354}
                    {11, "she"},             // {11, "she", 0.354}
                    {12, "always"},          // {12, "always", 0.272}
                    {12, "before"},          // {12, "before", 0.272}
                    {12, "heat"},            // {12, "heat", 0.272}
                    {12, "know"},            // {12, "know", 0.272}
                    {12, "last"},            // {12, "last", 0.272}
                    {12, "like"},            // {12, "like", 0.272}
                    {12, "me"},              // {12, "me", 0.272}
                    {12, "popping"},         // {12, "popping", 0.272}
                    {12, "savor"},           // {12, "savor", 0.272}
                    {12, "shot"},            // {12, "shot", 0.272}
                    {12, "sink"},            // {12, "sink", 0.272}
                    {12, "that"},            // {12, "that", 0.272}
                    {12, "the"},             // {12, "the", 0.173}
                    {12, "to"},              // {12, "to", 0.19}
                    {12, "you"},             // {12, "you", 0.21}
                    {13, "a"},               // {13, "a", 0.384}
                    {13, "does"},            // {13, "does", 0.431}
                    {13, "have"},            // {13, "have", 0.431}
                    {13, "soul"},            // {13, "soul", 0.384}
                    {13, "this"},            // {13, "this", 0.384}
                    {13, "unit"},            // {13, "unit", 0.431}
                    {14, "by"},              // {14, "by", 0.491}
                    {14, "digestive"},       // {14, "digestive", 0.164}
                    {14, "glands"},          // {14, "glands", 0.164}
                    {14, "no"},              // {14, "no", 0.425}
                    {14, "replaced"},        // {14, "replaced", 0.491}
                    {14, "soul"},            // {14, "soul", 0.126}
                    {14, "system"},          // {14, "system", 0.164}
                    {14, "tech"},            // {14, "tech", 0.491}
            }
    );


}