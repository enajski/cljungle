#!/usr/bin/env ruby -w
require 'rubygems'
require 'json'

raise Exception.new("You must pass in the pack id!") unless ARGV[0]

pack_id = ARGV[0]
note_extractor = lambda{|filename| filename.gsub(" ", "")}

data = JSON.parse(`curl --silent "http://www.freesound.org/api/packs/#{pack_id}/sounds/?api_key=47efd585321048819a2328721507ee23"`)
number_of_pages = data["num_pages"] || 1

number_of_pages.times do |page|
  data = JSON.parse(`curl --silent "http://www.freesound.org/api/packs/#{pack_id}/sounds/?api_key=47efd585321048819a2328721507ee23&p=#{page + 1}"`)
  sound_ids = data["sounds"].map{|sound| sound["id"]}
  note_names = data["sounds"].map{|sound| note_extractor.call(sound["original_filename"])}

  sound_ids.each_with_index do |sound_id, index|
    puts "#{sound_id} :#{note_names[index]}"
  end
end