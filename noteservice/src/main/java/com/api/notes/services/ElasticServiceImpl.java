package com.api.notes.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.notes.exception.NoteException;
import com.api.notes.models.Note;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ElasticServiceImpl implements ElasticService {

	@Autowired
	private RestHighLevelClient client;

	@Autowired
	private ObjectMapper mapper;

	private static final String index = "notes";

	private static final String type = "notes_data";

	@Override
	public void save(Note note) {
		try {
			Map<String, String> noteMap = mapper.convertValue(note, Map.class);
			IndexRequest request = new IndexRequest(index, type).id("" + note.getNoteId()).source(noteMap);
			IndexResponse response = client.index(request, RequestOptions.DEFAULT);
			log.info(response.status().toString());
		} catch (IOException e) {
			throw new NoteException(e.getMessage());
		}
	}

	@Override
	public void update(Note note) {
		UpdateRequest request = new UpdateRequest(index, type, note.getNoteId()+"");
		Map<String, String> noteMap = mapper.convertValue(note, Map.class);
		request.doc(noteMap);
		try {
			UpdateResponse response=client.update(request, RequestOptions.DEFAULT);
			log.info(response.status().toString());
		} catch (IOException e) {
			throw new NoteException(e.getMessage());
		}
	}

	@Override
	public void delete(String noteId) {
		DeleteRequest request=new DeleteRequest(index, type, noteId);
		try {
			DeleteResponse response=client.delete(request, RequestOptions.DEFAULT);
			log.info(response.toString());
		} catch (IOException e) {
			throw new NoteException(e.getMessage());
		}
	}

	@Override
	public List<Note> search(String search, String field) {
		SearchSourceBuilder builder=new SearchSourceBuilder();
		builder.query(QueryBuilders.termQuery(field, search));
		SearchRequest request=new SearchRequest("notes");
		request.source(builder);
		MatchQueryBuilder matchQueryBuilder=new MatchQueryBuilder(field, search);
		matchQueryBuilder.fuzziness(Fuzziness.AUTO); 
		matchQueryBuilder.prefixLength(3); 
		matchQueryBuilder.maxExpansions(10);
		builder.query(matchQueryBuilder);
		List<Note> notes= new ArrayList<Note>();
		try {
			SearchResponse searchResponse = client.search(request, RequestOptions.DEFAULT);
			searchResponse.getHits().spliterator().forEachRemaining(note-> {
				try {
					notes.add(mapper.readValue(note.getSourceAsString(), Note.class));
				} catch (JsonParseException|JsonMappingException e) {
					throw new NoteException(e.getMessage());
				} catch (IOException e) {
					throw new NoteException(e.getMessage());
				}
			});
		} catch (IOException e) {
			throw new NoteException(e.getMessage());
		}
		return notes;
	}

}
