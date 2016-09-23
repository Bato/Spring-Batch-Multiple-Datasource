package eu.mh.batch.processor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.batch.item.ItemProcessor;

import eu.mh.batch.model.Thing;

public class ThingItemProcessor implements ItemProcessor<Thing, Thing> {

    private static final Logger log = LoggerFactory.getLogger(ThingItemProcessor.class);

    @Override
    public Thing process(final Thing thing) throws Exception {
    	
        final String name = thing.getName().toUpperCase();
        final String descriptor = thing.getDescriptor().toUpperCase();

        final Thing transformedThing = new Thing(name, descriptor);

        log.info("Converting (" + thing + ") into (" + transformedThing + ")");

        return transformedThing;
    }

}
