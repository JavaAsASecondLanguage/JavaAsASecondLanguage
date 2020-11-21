package io.github.javaasasecondlanguage.homework04.mappers;

import io.github.javaasasecondlanguage.homework04.Record;
import io.github.javaasasecondlanguage.homework04.ops.mappers.LowerCaseMapper;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.github.javaasasecondlanguage.homework04.utils.AssertionUtils.assertRecordsEqual;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.applyMapperToAllRecords;
import static io.github.javaasasecondlanguage.homework04.utils.TestUtils.convertToRecords;

class LowerCaseMapperTest {

    @Test
    void general() {
        var mapper = new LowerCaseMapper("Text");
        System.out.println("there’s");
        List<Record> actualRecords = applyMapperToAllRecords(mapper, inputRecords);
        assertRecordsEqual(expectedRecords, actualRecords);
    }

    private static final List<Record> inputRecords = convertToRecords(
            new String[]{"DocId", "Text", "Author"},
            new Object[][]{
                    {1, "Elegant weapon for a more civilized age;", "Obi-Wan"},
                    {2, "Of course I know him, he's me!", "Obi-Wan"},
                    {3, "I Am The Senate.", "Sheev"},
                    {4, "I Don't Like Sand. It's Course And Rough and Irritating. And It Gets Everywhere.", "Anakin"},
                    {5, "Uh! So uncivilized.", "Obi-Wan"},
                    {6, "There’s always a bigger fish.", "Qui Gon"},
            }
    );

    private static final List<Record> expectedRecords = convertToRecords(
            new String[]{"DocId", "Text", "Author"},
            new Object[][]{
                    {1, "elegant weapon for a more civilized age;", "Obi-Wan"},
                    {2, "of course i know him, he's me!", "Obi-Wan"},
                    {3, "i am the senate.", "Sheev"},
                    {4, "i don't like sand. it's course and rough and irritating. and it gets everywhere.", "Anakin"},
                    {5, "uh! so uncivilized.", "Obi-Wan"},
                    {6, "there’s always a bigger fish.", "Qui Gon"},
            }
    );
}