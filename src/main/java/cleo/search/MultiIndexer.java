/*
 * Copyright (c) 2011 LinkedIn, Inc
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package cleo.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * MultiIndexer
 * 
 * @author jwu
 * @since 03/15, 2011
 * 
 * @param <E> Element to Index
 */
public class MultiIndexer<E extends Element> implements Indexer<E> {
  private String name = MultiIndexer.class.getSimpleName();
  private final List<Indexer<E>> indexerList = new ArrayList<Indexer<E>>();
  
  public MultiIndexer(List<Indexer<E>> indexers) {
    if(indexers != null) {
      for(Indexer<E> indexer : indexers) {
        if(indexer != null) {
          indexerList.add(indexer);
        }
      }
    }
  }
  
  public MultiIndexer(String name, List<Indexer<E>> indexers) {
    this(indexers);
    this.setName(name);
  }
  
  public final String getName() {
    return name;
  }
  
  public final void setName(String name) {
    this.name = name;
  }
  
  public final List<Indexer<E>> subIndexers() {
    return indexerList;
  }
  
  @Override
  public void flush() throws IOException {
    for(Indexer<E> indexer : indexerList) {
      indexer.flush();
    }
  }
  
  @Override
  public boolean index(E element) throws Exception {
    for(Indexer<E> indexer : indexerList) {
      if(indexer.index(element)) {
        return true;
      }
    }
    
    return false;
  }
}
